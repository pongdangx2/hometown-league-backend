package me.lkh.hometownleague.user.service;

import me.lkh.hometownleague.common.exception.common.user.DuplicateIdException;
import me.lkh.hometownleague.common.exception.common.user.DuplicateNameException;
import me.lkh.hometownleague.common.exception.common.user.NoSuchUserIdException;
import me.lkh.hometownleague.common.exception.common.user.WrongPasswordException;
import me.lkh.hometownleague.common.util.SecurityUtil;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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
     * @param user
     * @throws NoSuchAlgorithmException
     */
    public User loginCheck(User user) throws NoSuchAlgorithmException {

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
}
