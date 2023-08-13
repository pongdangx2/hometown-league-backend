package me.lkh.hometownleague.user.controller;

import me.lkh.hometownleague.common.exception.common.user.DuplicateIdException;
import me.lkh.hometownleague.common.exception.common.user.DuplicateNameException;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;

    @DisplayName("회원가입 테스트")
    @Transactional
    @Test
    void join() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        User user = new User(id, name, password);
        userService.join(user);

        User compareUser = userService.getUserById(user.getId());

        assertThat(compareUser.getId()).isEqualTo(user.getId());
        assertThat(compareUser.getName()).isEqualTo(user.getName());
    }

    @DisplayName("ID 중복 테스트")
    @Transactional
    @Test
    void duplicateId() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        User user = new User(id, name, password);
        userService.join(user);

        User compareUser = new User(id, name+"1", password);

        assertThrows(DuplicateIdException.class, () -> {
            userService.join(compareUser);
        });
    }

    @DisplayName("닉네임 중복 테스트")
    @Transactional
    @Test
    void duplicateName() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        User user = new User(id, name, password);
        userService.join(user);

        User compareUser = new User(id+"1", name, password);

        assertThrows(DuplicateNameException.class, () -> {
            userService.join(compareUser);
        });
    }
}