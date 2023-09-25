package me.lkh.hometownleague.matching.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.matching.service.MatchingService;
import me.lkh.hometownleague.session.domain.AuthCheck;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AuthCheck
@RestController
@RequestMapping("/matching")
public class MatchingController {

    private final MatchingService matchingService;
    private final SessionService sessionService;

    public MatchingController(MatchingService matchingService, SessionService sessionService) {
        this.matchingService = matchingService;
        this.sessionService = sessionService;
    }

    /** 신규 매칭 요청
     *
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/request")
    public CommonResponse makeMatchingRequest(@RequestBody Map<String, Object> teamIdMap, HttpServletRequest httpServletRequest){
        Integer teamId = Integer.valueOf(teamIdMap.get("teamId").toString());
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());

        matchingService.makeMatchingRequest(userSession.getUserId(), teamId);

        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }
}
