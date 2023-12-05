package me.lkh.hometownleague.matching.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.matching.domain.MatchingResultReportRequest;
import me.lkh.hometownleague.matching.service.MatchingService;
import me.lkh.hometownleague.session.domain.AuthCheck;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.session.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AuthCheck
@RestController
@RequestMapping("/matching")
public class MatchingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MatchingService matchingService;
    private final SessionService sessionService;

    public MatchingController(MatchingService matchingService, SessionService sessionService) {
        this.matchingService = matchingService;
        this.sessionService = sessionService;
    }

    /**
     * 신규 매칭 요청
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

    /**
     * 사용자가 속한 팀의 매칭 요청 목록 조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public CommonResponse selectMatching(@PathVariable("userId") String userId){
        return new CommonResponse(matchingService.selectMatching(userId));
    }

    /**
     * 매칭의 상세정보 조회
     * @param matchingRequestId
     * @return
     */
    @GetMapping("/{matchingRequestId}/detail")
    public CommonResponse selectMatchingDetail(@PathVariable("matchingRequestId") Integer matchingRequestId){
        return new CommonResponse(matchingService.selectMatchingDetail(matchingRequestId));
    }

    /**
     * 아직 매칭이 되지 않았을 경우 매칭 취소
     * @param matchingRequestId
     * @param httpServletRequest
     * @return
     */
    @DeleteMapping("/{matchingRequestId}")
    public CommonResponse deleteMatchingRequest(@PathVariable("matchingRequestId") Integer matchingRequestId, HttpServletRequest httpServletRequest) {
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        matchingService.deleteMatchingRequest(matchingRequestId, userSession.getUserId());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 매칭 수락
     * @param param
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/accept")
    public CommonResponse acceptMatching(@RequestBody Map<String, Integer> param, HttpServletRequest httpServletRequest){
        if(!param.containsKey("matchingRequestId")){
            throw new IllegalArgumentException("matchingRequestId는 필수입니다.");
        }
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        matchingService.acceptMatching(param.get("matchingRequestId"), userSession.getUserId());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 매칭 거절
     * @param param
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/refuse")
    public CommonResponse refuseMatching(@RequestBody Map<String, Integer> param, HttpServletRequest httpServletRequest) {
        if(!param.containsKey("matchingRequestId")){
            throw new IllegalArgumentException("matchingRequestId는 필수입니다.");
        }
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        matchingService.refuseMatching(param.get("matchingRequestId"), userSession.getUserId());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    @PostMapping("/result")
    public CommonResponse reportResult(@RequestBody MatchingResultReportRequest matchingResult, HttpServletRequest httpServletRequest){
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        matchingService.reportResult(matchingResult, userSession.getUserId());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    @GetMapping("/history/{teamId}")
    public CommonResponse selectMatchHistory(@PathVariable("teamId")Integer teamId, @RequestParam Integer page){
        return new CommonResponse(matchingService.selectMatchHistory(teamId, page), matchingService.selectMatchHistoryCount(teamId));
    }
}
