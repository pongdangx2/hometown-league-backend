package me.lkh.hometownleague.team.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.session.domain.AuthCheck;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.team.domain.request.UpdateTeamPlayLocationRequest;
import me.lkh.hometownleague.team.domain.request.UpdateTeamPlayTimeRequest;
import me.lkh.hometownleague.team.domain.request.UpdateTeamRequest;
import me.lkh.hometownleague.team.service.TeamService;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.team.domain.request.MakeTeamRequest;
import me.lkh.hometownleague.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 팀생성/팀정보변경 등 팀관련 비즈니스로직을 처리
 * URL : /team/**
 * @author leekh
 * @see me.lkh.hometownleague.common.exception.HomeTownLeagueExceptionHandler
 */
@AuthCheck
@RestController
@RequestMapping("/team")
public class TeamController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TeamService teamService;
    private final SessionService sessionService;

    public TeamController(TeamService teamService, SessionService sessionService) {
        this.teamService = teamService;
        this.sessionService = sessionService;
    }

    /**
     * 팀 생성
     * @param makeTeamRequest
     * @param httpServletRequest
     * @return
     */
    @PostMapping
    public CommonResponse makeTeam(@RequestBody MakeTeamRequest makeTeamRequest, HttpServletRequest httpServletRequest){

        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        Team team = Team.forCreatingTeam(makeTeamRequest.getName(), userSession.getUserId(), "", makeTeamRequest.getDescription(), makeTeamRequest.getKind());
        teamService.makeTeam(team, makeTeamRequest.getTime(), makeTeamRequest.getLocation());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 팀명이 이미존재하는지 확인
     * @param name 팀명
     * @return 이미 존재하면 "Y"
     * @throws CommonErrorException 기타 에러가 발생한 경우
     */
    @GetMapping("/is-duplicate/{name}")
    public CommonResponse isDuplicate(@PathVariable String name) throws CommonErrorException{
        boolean isDuplicate = teamService.isDuplicate(name);
        return new CommonResponse<>(isDuplicate ? "Y" : "N");
    }

    /**
     * 팀 삭제
     * @param teamId
     * @param httpServletRequest
     * @return
     */
    @DeleteMapping("/{teamId}")
    public CommonResponse deleteTeam(@PathVariable Integer teamId, HttpServletRequest httpServletRequest){
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        teamService.deleteTeam(userSession.getUserId(), teamId);
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 팀 조회
     * @param teamId
     * @return
     */
    @GetMapping("/{teamId}")
    public CommonResponse selectTeam(@PathVariable Integer teamId, HttpServletRequest httpServletRequest){
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        return new CommonResponse<>(teamService.selectTeam(userSession.getUserId(), teamId));
    }

    /**
     * 팀 기본정보 업데이트 (장소/시간 제외)
     * @param updateTeamRequest
     * @param httpServletRequest
     * @return
     */
    @PatchMapping
    public CommonResponse updateTeam(@RequestBody UpdateTeamRequest updateTeamRequest, HttpServletRequest httpServletRequest){
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        // 정보 업데이트
        teamService.updateTeam(Team.forUpdateTeam(updateTeamRequest.getId(), updateTeamRequest.getName(), updateTeamRequest.getDescription()),
                userSession.getUserId());

        // 업데이트된 정보 다시 조회하여 응답
        return new CommonResponse<>(teamService.selectTeam(userSession.getUserId(), updateTeamRequest.getId()));
    }

    /**
     * 팀 운동 시간 수정
     * @param updateTeamPlayTimeRequest
     * @param httpServletRequest
     * @return
     */
    @PutMapping("/play-time")
    public CommonResponse updateTeamPlayTime(@RequestBody UpdateTeamPlayTimeRequest updateTeamPlayTimeRequest, HttpServletRequest httpServletRequest){
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        teamService.updateTeamPlayTime(updateTeamPlayTimeRequest.getTeamId(), userSession.getUserId(), updateTeamPlayTimeRequest.getTime());

        // 업데이트된 정보 다시 조회하여 응답
        return new CommonResponse<>(teamService.selectTeam(userSession.getUserId(), updateTeamPlayTimeRequest.getTeamId()));
    }

    /**
     * 팀 운동 장소 수정
     * @param updateTeamPlayLocationRequest
     * @param httpServletRequest
     * @return
     */
    @PutMapping("/play-location")
    public CommonResponse updateTeamPlayLocation(@RequestBody UpdateTeamPlayLocationRequest updateTeamPlayLocationRequest, HttpServletRequest httpServletRequest){
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        teamService.updateTeamPlayLocation(updateTeamPlayLocationRequest.getTeamId(), userSession.getUserId(), updateTeamPlayLocationRequest.getLocation());

        // 업데이트된 정보 다시 조회하여 응답
        return new CommonResponse<>(teamService.selectTeam(userSession.getUserId(), updateTeamPlayLocationRequest.getTeamId()));
    }

    /**
     * 팀 소속 선수 조회
     * @param teamId
     * @return
     */
    @GetMapping("/{teamId}/players")
    public CommonResponse selectUserOfTeam(@PathVariable Integer teamId){
        List<User> userList = teamService.selectUserOfTeam(teamId);
        Map<String, Object> result = new HashMap<>();
        result.put("users", userList);
        result.put("count", userList.size());
        return new CommonResponse(result);
    }

    /**
     * 주장 변경
     * @param teamId
     * @param userIdMap
     * @param httpServletRequest
     * @return
     */
    @PatchMapping("/{teamId}/owner")
    public CommonResponse updateTeamOwner(@PathVariable Integer teamId, @RequestBody Map<String, Object> userIdMap, HttpServletRequest httpServletRequest){
        String userId = userIdMap.get("userId").toString();
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        teamService.updateTeamOwner(userSession.getUserId(), teamId, userId);
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    @GetMapping
    public CommonResponse selectTeamList( @RequestParam(name = "legal-code", required = false) Integer legalCode
                                         ,@RequestParam(name = "from-score", required = false) Integer fromScore
                                         ,@RequestParam(name = "to-score", required = false) Integer toScore
                                         ,@RequestParam(name = "day-of-Week", required = false) Integer dayOfWeek
                                         ,@RequestParam(name = "time", required = false) String time
                                         ,@RequestParam(required = false) String name){

        logger.debug("selectTeamList:" + legalCode + ", " + fromScore + ", " + toScore + ", " + name);
        return new CommonResponse<>(teamService.selectTeamList(legalCode, fromScore, toScore, dayOfWeek, time, name));
    }
}
