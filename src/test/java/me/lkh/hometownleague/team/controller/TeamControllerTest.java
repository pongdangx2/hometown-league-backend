package me.lkh.hometownleague.team.controller;

import me.lkh.hometownleague.common.exception.team.NoSuchTeamIdException;
import me.lkh.hometownleague.common.exception.team.NotOwnerException;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.team.domain.TeamPlayLocation;
import me.lkh.hometownleague.team.domain.TeamPlayTime;
import me.lkh.hometownleague.team.repository.TeamRepository;
import me.lkh.hometownleague.team.service.TeamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TeamControllerTest {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRepository;

    @DisplayName("팀생성_성공")
    @Transactional
    @Test
    void 팀생성_성공(){
        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, "서울과기대",null, 37.6317692339419, 127.0803445512275, "11350103", "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232"));

        String teamName = "testTeamName";
        Team team = Team.forCreatingTeam(teamName, "testUser@gmail.com", "temp", "테스트로만든팀",1);
        teamService.makeTeam(team, time, location);

        Team selectedTeam = teamRepository.selectTeamByName(teamName);
        assertThat(team.getName()).isEqualTo(selectedTeam.getName());
        assertThat(team.getCiPath()).isEqualTo(selectedTeam.getCiPath());
        assertThat(team.getDescription()).isEqualTo(selectedTeam.getDescription());
        assertThat(team.getKind()).isEqualTo(selectedTeam.getKind());

        Team selectedTeam2 = teamRepository.selectTeam(Team.forSelectTeam(selectedTeam.getId()));
        assertThat(team.getOwnerId()).isEqualTo(selectedTeam2.getOwnerId());

        List<TeamPlayLocation> locations = teamRepository.selectTeamPlayLocation(selectedTeam.getId());
        for(int i = 0; i < locations.size(); i++){
            TeamPlayLocation location1 = locations.get(i);
            TeamPlayLocation location2 = location.get(i);

            assertThat(location1.getName()).isEqualTo(location2.getName());
            assertThat(location1.getJibunAddress()).isEqualTo(location2.getJibunAddress());
            assertThat(location1.getLatitude()).isEqualTo(location2.getLatitude());
            assertThat(location1.getRoadAddress()).isEqualTo(location2.getRoadAddress());
            assertThat(location1.getLegalCode()).isEqualTo(location2.getLegalCode());
            assertThat(location1.getLongitude()).isEqualTo(location2.getLongitude());
        }

        List<TeamPlayTime> times = teamRepository.selectTeamPlayTime(selectedTeam.getId());

        for(int i = 0; i < times.size(); i++){
            TeamPlayTime time1 = times.get(i);
            TeamPlayTime time2 = time.get(i);

            assertThat(time1.getDayOfWeek()).isEqualTo(time2.getDayOfWeek());
            assertThat(time1.getPlayTimeTo()).isEqualTo(time2.getPlayTimeTo());
            assertThat(time1.getPlayTimeFrom()).isEqualTo(time2.getPlayTimeFrom());
        }

    }

    @DisplayName("팀명중복확인_미중복")
    @Transactional
    @Test
    void 중복확인_미중복() {

        String teamName = "!@#$%%^!@%!$!$!$";
        assertThat(teamService.isDuplicate(teamName)).isEqualTo(false);
    }

    @DisplayName("팀명중복확인_중복")
    @Transactional
    @Test
    void 중복확인_중복() {
        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, "서울과기대",null, 37.6317692339419, 127.0803445512275, "11350103", "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232"));

        String teamName = "testTeamName";
        Team team = Team.forCreatingTeam(teamName, "testUser@gmail.com", "temp", "테스트로만든팀",1);
        teamService.makeTeam(team, time, location);

        assertThat(teamService.isDuplicate(teamName)).isEqualTo(true);
    }

    @DisplayName("팀삭제_성공")
    @Transactional
    @Test
    void 팀삭제_성공() {

        String ownerId = "testUser@gmail.com";

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, "서울과기대",null, 37.6317692339419, 127.0803445512275, "11350103", "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232"));

        String teamName = "testTeamName";
        Team team = Team.forCreatingTeam(teamName, ownerId, "temp", "테스트로만든팀",1);
        teamService.makeTeam(team, time, location);

        Team selectedTeam = teamRepository.selectTeamByName(teamName);
        assertThatCode(() -> teamService.deleteTeam(ownerId, selectedTeam.getId())).doesNotThrowAnyException();
    }

    @DisplayName("팀삭제_실패_팀ID미존재")
    @Transactional
    @Test
    void 팀삭제_실패_팀ID미존재() {

        String ownerId = "testUser@gmail.com";

        assertThrows(NoSuchTeamIdException.class, () -> {
            teamService.deleteTeam(ownerId, -1);
        });
    }

    @DisplayName("팀삭제_실패_소유주가 아닐때")
    @Transactional
    @Test
    void 팀삭제_실패_소유주아님() {

        String ownerId = "testUser@gmail.com";

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, "서울과기대",null, 37.6317692339419, 127.0803445512275, "11350103", "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232"));

        String teamName = "testTeamName";
        Team team = Team.forCreatingTeam(teamName, ownerId, "temp", "테스트로만든팀",1);
        teamService.makeTeam(team, time, location);

        Team selectedTeam = teamRepository.selectTeamByName(teamName);
        assertThrows(NotOwnerException.class, () -> {
            teamService.deleteTeam(ownerId+"1", selectedTeam.getId());
        });
    }

    @DisplayName("팀조회_성공")
    @Transactional
    @Test
    void 팀조회_성공() {

        String ownerId = "testUser@gmail.com";

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, "서울과기대",null, 37.6317692339419, 127.0803445512275, "11350103", "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232"));

        String teamName = "testTeamName";
        Team team = Team.forCreatingTeam(teamName, ownerId, "temp", "테스트로만든팀",1);
        teamService.makeTeam(team, time, location);

        Integer teamId = teamRepository.selectTeamByName(teamName).getId();

        Team selectedTeam = teamService.selectTeam(teamId);
        // -----
        assertThat(teamId).isEqualTo(selectedTeam.getId());
        assertThat(team.getName()).isEqualTo(selectedTeam.getName());
        assertThat(team.getOwnerId()).isEqualTo(selectedTeam.getOwnerId());
        assertThat(team.getCiPath()).isEqualTo(selectedTeam.getCiPath());
        assertThat(team.getDescription()).isEqualTo(selectedTeam.getDescription());
        assertThat(team.getKind()).isEqualTo(selectedTeam.getKind());

        List<TeamPlayLocation> locations = selectedTeam.getLocation();
        for(int i = 0; i < locations.size(); i++){
            TeamPlayLocation location1 = locations.get(i);
            TeamPlayLocation location2 = location.get(i);

            assertThat(location1.getName()).isEqualTo(location2.getName());
            assertThat(location1.getJibunAddress()).isEqualTo(location2.getJibunAddress());
            assertThat(location1.getLatitude()).isEqualTo(location2.getLatitude());
            assertThat(location1.getRoadAddress()).isEqualTo(location2.getRoadAddress());
            assertThat(location1.getLegalCode()).isEqualTo(location2.getLegalCode());
            assertThat(location1.getLongitude()).isEqualTo(location2.getLongitude());
        }

        List<TeamPlayTime> times = selectedTeam.getTime();

        for(int i = 0; i < times.size(); i++){
            TeamPlayTime time1 = times.get(i);
            TeamPlayTime time2 = time.get(i);

            assertThat(time1.getDayOfWeek()).isEqualTo(time2.getDayOfWeek());
            assertThat(time1.getPlayTimeTo()).isEqualTo(time2.getPlayTimeTo());
            assertThat(time1.getPlayTimeFrom()).isEqualTo(time2.getPlayTimeFrom());
        }
    }

    @DisplayName("팀조회_실패_팀미존재")
    @Transactional
    @Test
    void 팀조회_실패_팀미존재() {
        assertThrows(NoSuchTeamIdException.class, () -> teamService.selectTeam(-1));
    }

    @DisplayName("팀기본정보 업데이트_성공")
    @Transactional
    @Test
    void 팀기본정보_업데이트() {
        String ownerId = "testUser@gmail.com";

        List<TeamPlayTime> time = new ArrayList<>();
        time.add(new TeamPlayTime(null, null, 1, "1000", "1200"));
        time.add(new TeamPlayTime(null, null, 2, "2000", "2200"));

        List<TeamPlayLocation> location = new ArrayList<>();
        location.add(new TeamPlayLocation(null, "서울과기대",null, 37.6317692339419, 127.0803445512275, "11350103", "서울특별시 노원구 공릉동 172", "서울특별시 노원구 공릉로 232"));

        String teamName = "testTeamName";
        Team team = Team.forCreatingTeam(teamName, ownerId, "temp", "테스트로만든팀",1);
        teamService.makeTeam(team, time, location);

        Integer teamId = teamRepository.selectTeamByName(teamName).getId();

        Team updateTeam = Team.forUpdateTeam(teamId, teamName+"1", team.getDescription()+"1");
        teamService.updateTeam(updateTeam,ownerId);
        Team selectedTeam = teamService.selectTeam(teamId);

        assertThat(selectedTeam.getName()).isEqualTo(updateTeam.getName());
        assertThat(selectedTeam.getDescription()).isEqualTo(updateTeam.getDescription());
    }

}