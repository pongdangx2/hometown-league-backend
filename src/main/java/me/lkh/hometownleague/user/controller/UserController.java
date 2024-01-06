package me.lkh.hometownleague.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.session.domain.AuthCheck;
import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.user.domain.JoinDuplicateCheck;
import me.lkh.hometownleague.user.domain.request.JoinRequest;
import me.lkh.hometownleague.user.domain.request.LoginRequest;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.user.domain.request.UpdateRequest;
import me.lkh.hometownleague.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * 유저 회원가입/로그인, 유저 정보 조회/수정 등 유저 관련된 비즈니스로직을 수행
 * URL : /user/**
 * @author leekh
 * @see me.lkh.hometownleague.common.exception.HomeTownLeagueExceptionHandler
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    private final SessionService sessionService;

    public UserController(SessionService redisSessionService, UserService userService) {
        this.sessionService = redisSessionService;
        this.userService = userService;
    }

    /**
     * 사용자 로그인
     *  1. ID/PW 체크
     *  2. 세션 생성
     *  3. 응답
     * @param loginRequest 로그인할 사용자의 정보 - id, password 는 필수로 포함해야함.
     * @param response
     * @return
     * @throws NoSuchAlgorithmException
     * @see me.lkh.hometownleague.user.service.UserService#loginCheck(User) 
     */
    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequest loginRequest,
                                HttpServletResponse response) throws NoSuchAlgorithmException {
        User user = new User(loginRequest.getId(), loginRequest.getPassword());

        // 1. ID/PW 체크
        User checkedUser = userService.loginCheck(user);

        // 2. 세션 생성
        UserSession userSession = sessionService.getSession(checkedUser);

        // 3. 세션 저장
        sessionService.login(userSession);

        // 4. 응답쿠키 설정 (SET-COOKIE)
        SessionUtil.setSessionCookie(response, userSession.getSessionId());

        // 5. 응답 body에  세션ID 추가
        User responseUser = new User(checkedUser.getId(), checkedUser.getNickname(), checkedUser.getPassword(), checkedUser.getDescription(), checkedUser.getCiPath(), checkedUser.getUseYn(), checkedUser.getCreateTimestamp(), checkedUser.getModifiedTimestamp(), userSession.getSessionId());

        return new CommonResponse<>(responseUser);
    }

    /**
     * 회원가입
     * @param joinRequest
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/join")
    public CommonResponse join(@RequestBody JoinRequest joinRequest) throws NoSuchAlgorithmException {
        User user = new User(joinRequest.getId(), joinRequest.getNickname(), joinRequest.getPassword(), joinRequest.getDescription());
        logger.debug("user:" + user);
        userService.join(user);
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * ID/닉네임이 이미존재하는지 확인
     * @param type "id" : ID 중복여부 체크 , "nickname" : 닉네임 중복여부 체크
     * @param value id 혹은 nickname 값
     * @return 이미 존재하면 "Y"
     */
    @GetMapping("/is-duplicate")
    public CommonResponse isDuplicate(@RequestParam(defaultValue = "id") String type
                                    , @RequestParam String value) {
        boolean isDuplicate = userService.isDuplicate(new JoinDuplicateCheck(type, value));
        return new CommonResponse<>(isDuplicate ? "Y" : "N");
    }

    /**
     * ID로 User정보를 조회
     * @param id 조회하고자 하는 User의 ID
     * @return
     */
    @AuthCheck
    @GetMapping("/{id}")
    public CommonResponse getUser(@PathVariable("id") String id) {
        User user = userService.selectUserById(id);
        return new CommonResponse<>(user);
    }

    /**
     * 사용자 정보를 수정
     * @param updateRequest 수정할 User정보 - User ID는 필수이며, 닉네임, 패스워드, 소개글을 수정할 수 있다.
     * @return
     * @throws NoSuchAlgorithmException
     */
    @AuthCheck
    @PatchMapping
    public CommonResponse updateUser(@RequestBody UpdateRequest updateRequest) throws NoSuchAlgorithmException {
        User user = new User(updateRequest.getId(), updateRequest.getNickname(), updateRequest.getPassword(), updateRequest.getDescription());
        userService.updateUser(user);
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 사용자가 속한 팀을 조회
     * @return
     */
    @AuthCheck
    @GetMapping("/team")
    public CommonResponse getUserTeams(HttpServletRequest httpServletRequest) {
        UserSession userSession = sessionService.getUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        return new CommonResponse<>(userService.selectTeamOfUser(userSession.getUserId()));
    }

    /**
     * 로그아웃
     * @param httpServletRequest
     * @return
     */
    @AuthCheck
    @DeleteMapping("/logout")
    public CommonResponse logout(HttpServletRequest httpServletRequest){
        sessionService.deleteUserSession(SessionUtil.getSessionIdFromRequest(httpServletRequest).get());
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }
}
