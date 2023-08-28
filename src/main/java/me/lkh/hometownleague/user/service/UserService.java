package me.lkh.hometownleague.user.service;

import jakarta.servlet.http.HttpServletResponse;
import me.lkh.hometownleague.common.exception.common.user.DuplicateIdException;
import me.lkh.hometownleague.common.exception.common.user.DuplicateNameException;
import me.lkh.hometownleague.common.exception.common.user.NoSuchUserIdException;
import me.lkh.hometownleague.common.exception.common.user.WrongPasswordException;
import me.lkh.hometownleague.common.util.SecurityUtil;
import me.lkh.hometownleague.user.domain.JoinDuplicateCheck;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.domain.request.LoginRequest;
import me.lkh.hometownleague.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * 유저 회원가입/로그인 등 세션처리가 필요없는 API에서 사용하는 서비스
 * @author leekh
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 로그인 체크
     *  - ID 존재하는지 확인
     *  - PW가 일치하는지 확인
     * @param user User 인스턴스는 id, password를 필수로 포함하고 있어야함.
     * @return ID로 조회한 User
     * @throws NoSuchAlgorithmException 패스워드 해시 함수에서 발생할 수 있는 Exception
     * @throws WrongPasswordException 패스워드가 일치하지 않는 경우
     * @throws NoSuchUserIdException ID가 존재하지 않는 경우
     * @see me.lkh.hometownleague.user.controller.UserController#login(LoginRequest loginRequest, HttpServletResponse response)
     */
    public User loginCheck(User user) throws NoSuchAlgorithmException, WrongPasswordException, NoSuchUserIdException {

        final User encryptedUser = new User(user.getId()
                , SecurityUtil.hashEncrypt(user.getPassword()));

        Optional<User> optionalUser = Optional.ofNullable(userRepository.selectUserById(encryptedUser.getId()));
        optionalUser.ifPresentOrElse(selectedUser -> {
                        // 패스워드가 일치하지 않는 경우
                        if(!encryptedUser.getPassword().equals(selectedUser.getPassword())){
                            throw new WrongPasswordException();
                        }
                    }
                    // ID가 존재하지 않는 경우
                    , () -> { throw new NoSuchUserIdException(); }
                );
        return optionalUser.get();
    }

    /**
     * 회원가입
     * @param user
     * @throws NoSuchAlgorithmException
     */
    public void join(User user) throws NoSuchAlgorithmException {
        User checkUser = new User(user.getId(), user.getNickname(), null);

        // ID, 닉네임 중복체크
        Optional.ofNullable(userRepository.selectUserDupCheck(checkUser))
                .ifPresent(userDupCheck -> {
                    // ID가 중복되는 경우
                    if ("Y".equals(userDupCheck.getIdDupYn()))
                        throw new DuplicateIdException();

                    // 닉네임이 중복되는 경우
                    if ("Y".equals(userDupCheck.getNameDupYn()))
                        throw new DuplicateNameException();
                });

        User encryptedUser = new User(user.getId()
                                    , user.getNickname()
                                    , SecurityUtil.hashEncrypt(user.getPassword())
                                    , user.getDescription());

        // user 데이터 삽입
        userRepository.insertUser(encryptedUser);
    }

    /**
     * 중복체크
     * @param joinDuplicateCheck    type : "nickname" or "id" / value : id 혹은 닉네임 값
     * @return 이미 존재하는 경우 true
     */
    public boolean isDuplicate(JoinDuplicateCheck joinDuplicateCheck){
        return Optional.ofNullable(userRepository.selectIsDuplicate(joinDuplicateCheck)).isPresent();
    }

    /**
     * ID로 유저를 조회
     * @param id 조회하고자하는 User의 ID
     * @return 조회된 User
     */
    public User selectUserById(String id) {
        Optional<User> selectedUser = Optional.ofNullable(userRepository.selectUserById(id));
        selectedUser.orElseThrow(() -> { throw new NoSuchUserIdException(); });
        return selectedUser.get();
    }
}
