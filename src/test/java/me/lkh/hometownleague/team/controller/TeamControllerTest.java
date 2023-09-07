package me.lkh.hometownleague.team.controller;

import me.lkh.hometownleague.team.repository.TeamRepository;
import me.lkh.hometownleague.team.service.TeamService;
import me.lkh.hometownleague.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TeamControllerTest {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;


    @DisplayName("팀명중복확인테스트")
    @Transactional
    @Test
    void login() throws NoSuchAlgorithmException {
        String id = "testID!@#$%";
        String name = "testNAME!@#$^";
        String password = "testPassw0rD";
        String description = "test소개글";
        User user = new User(id, name, password, description);
    }
}