package me.lkh.hometownleague.user.controller;

import me.lkh.hometownleague.common.exception.user.DuplicateIdException;
import me.lkh.hometownleague.common.exception.user.DuplicateNameException;
import me.lkh.hometownleague.common.exception.user.NoSuchUserIdException;
import me.lkh.hometownleague.common.exception.user.WrongPasswordException;
import me.lkh.hometownleague.common.util.SecurityUtil;
import me.lkh.hometownleague.user.domain.JoinDuplicateCheck;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.repository.UserRepository;
import me.lkh.hometownleague.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @DisplayName("로그인 테스트")
    @Transactional
    @Test
    void login() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        assertThatCode(() -> userService.loginCheck(user)).doesNotThrowAnyException();
    }

    @DisplayName("로그인 - ID미존재 테스트")
    @Transactional
    @Test
    void loginNoSuchId() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        User tmpUser = new User(id+"1", name, password);

        assertThrows(NoSuchUserIdException.class, () -> {
            userService.loginCheck(tmpUser);
        });
    }

    @DisplayName("로그인 - 패스워드 불일치 테스트")
    @Transactional
    @Test
    void loginWrongPassword() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        User tmpUser = new User(id, name, password+"1");

        assertThrows(WrongPasswordException.class, () -> {
            userService.loginCheck(tmpUser);
        });
    }

    @DisplayName("회원가입 테스트")
    @Transactional
    @Test
    void join() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        User compareUser = userRepository.selectUserById(user.getId());

        assertThat(compareUser.getId()).isEqualTo(user.getId());
        assertThat(compareUser.getNickname()).isEqualTo(user.getNickname());
    }

    @DisplayName("회원가입 - ID 중복 테스트")
    @Transactional
    @Test
    void duplicateId() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        User compareUser = new User(id, name+"1", password);

        assertThrows(DuplicateIdException.class, () -> {
            userService.join(compareUser);
        });
    }

    @DisplayName("회원가입 - 닉네임 중복 테스트")
    @Transactional
    @Test
    void duplicateName() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        User compareUser = new User(id+"1", name, password);

        assertThrows(DuplicateNameException.class, () -> userService.join(compareUser));
    }

    @DisplayName("닉네임중복체크 - 중복")
    @Transactional
    @Test
    void nickNameDupCheck_dup() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        boolean isDuplicate = userService.isDuplicate(new JoinDuplicateCheck("nickname", name));
        assertThat(isDuplicate).isEqualTo(true);
    }

    @DisplayName("닉네임중복체크 - 미중복")
    @Transactional
    @Test
    void nickNameDupCheck() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        boolean isDuplicate = userService.isDuplicate(new JoinDuplicateCheck("nickname", name + "1"));
        assertThat(isDuplicate).isEqualTo(false);
    }

    @DisplayName("id중복체크 - 중복")
    @Transactional
    @Test
    void idDupCheck_dup() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        boolean isDuplicate = userService.isDuplicate(new JoinDuplicateCheck("id", id));
        assertThat(isDuplicate).isEqualTo(true);
    }

    @DisplayName("닉네임중복체크 - 미중복")
    @Transactional
    @Test
    void idDupCheck() throws NoSuchAlgorithmException {

        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        boolean isDuplicate = userService.isDuplicate(new JoinDuplicateCheck("id", id + "1"));
        assertThat(isDuplicate).isEqualTo(false);
    }

    @DisplayName("ID로 사용자 조회")
    @Transactional
    @Test
    void selectUserById() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        User selectedUser = userService.selectUserById(id);
        assertThat(selectedUser.getId()).isEqualTo(user.getId());
        assertThat(selectedUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(selectedUser.getDescription()).isEqualTo(user.getDescription());
        assertThat(selectedUser.getPassword()).isEqualTo(SecurityUtil.hashEncrypt(user.getPassword()));
    }

    @DisplayName("ID로 사용자 조회-미존재")
    @Transactional
    @Test
    void selectUserById_notExist() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        assertThrows(NoSuchUserIdException.class, () -> userService.selectUserById(id + "!@#%^@#!"));
    }

    @DisplayName("사용자 정보 업데이트")
    @Transactional
    @Test
    void updateUser() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        name = "update:" + name;
        password = "update:" + password;
        description = "update:" + description;
        User updateUser = new User(id, name, password, description);

        userService.updateUser(updateUser);
        User selectedUser = userService.selectUserById(updateUser.getId());
        assertThat(selectedUser.getNickname()).isEqualTo(updateUser.getNickname());
        assertThat(selectedUser.getDescription()).isEqualTo(updateUser.getDescription());
        assertThat(selectedUser.getPassword()).isEqualTo(SecurityUtil.hashEncrypt(updateUser.getPassword()));
    }

    @DisplayName("사용자 정보 업데이트_실패")
    @Transactional
    @Test
    void updateUser_fail() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
        userService.join(user);

        id = "update:" + id;
        name = "update:" + name;
        password = "update:" + password;
        description = "update:" + description;
        User updateUser = new User(id, name, password, description);

        assertThrows(NoSuchUserIdException.class, () -> userService.updateUser(updateUser));
    }
}