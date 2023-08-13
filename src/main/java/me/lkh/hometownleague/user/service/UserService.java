package me.lkh.hometownleague.user.service;

import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.exception.common.user.DuplicateIdException;
import me.lkh.hometownleague.common.exception.common.user.DuplicateNameException;
import me.lkh.hometownleague.common.util.SecurityUtil;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.domain.UserDupCheck;
import me.lkh.hometownleague.user.service.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    public UserService(UserRepository userDao) {
        this.userRepository = userDao;
    }

    public User getUserById(String id) {
        return userRepository.selectUserById(id);
    }

    public void join(User user) throws NoSuchAlgorithmException {
        User checkUser = new User(user.getId(), user.getName());

        // ID, 닉네임 중복체크
        Optional.ofNullable(userRepository.selectUserDupCheck(checkUser))
                .ifPresent(userDupCheck -> {
                    if ("Y".equals(userDupCheck.getIdDupYn()))
                        throw new DuplicateIdException();
                    if ("Y".equals(userDupCheck.getNameDupYn()))
                        throw new DuplicateNameException();
                });

        User encryptedUser = new User(user.getId()
                                    , user.getName()
                                    , SecurityUtil.encrypt(user.getPassword()));

        // user 데이터 삽입
        userRepository.insertUser(encryptedUser);
    }
}
