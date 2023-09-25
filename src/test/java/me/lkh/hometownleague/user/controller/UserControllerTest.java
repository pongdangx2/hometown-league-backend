package me.lkh.hometownleague.user.controller;

import me.lkh.hometownleague.common.exception.user.DuplicateIdException;
import me.lkh.hometownleague.common.exception.user.DuplicateNameException;
import me.lkh.hometownleague.common.exception.user.NoSuchUserIdException;
import me.lkh.hometownleague.common.exception.user.WrongPasswordException;
import me.lkh.hometownleague.common.util.SecurityUtil;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.team.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.domain.TeamPlayTime;
import me.lkh.hometownleague.team.repository.TeamRepository;
import me.lkh.hometownleague.team.service.TeamService;
import me.lkh.hometownleague.user.domain.JoinDuplicateCheck;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.domain.UserTeam;
import me.lkh.hometownleague.user.repository.UserRepository;
import me.lkh.hometownleague.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

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

    @DisplayName("사용자가 속한 팀 조회")
    @Transactional
    @Test
    void 사용자소속팀조회() {
        String ownerId = "testUser!gmail.com";

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, "서울과기대",null, 37.6317692339419, 127.0803445512275, "11350103", "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232"));

        String teamName = "testTeamName";
        Team team = Team.forCreatingTeam(teamName, ownerId, "temp", "테스트로만든팀",1);
        teamService.makeTeam(team, time, location);

        Integer teamId = teamRepository.selectTeamByName(teamName).getId();

        UserTeam selectedUserTeam = userService.selectTeamOfUser(ownerId).get(0);
        assertThat(teamId).isEqualTo(selectedUserTeam.getId());
        assertThat(team.getName()).isEqualTo(selectedUserTeam.getName());
        assertThat("Y").isEqualTo(selectedUserTeam.getOwnerYn());
    }
}