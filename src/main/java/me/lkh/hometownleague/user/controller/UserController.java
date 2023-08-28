package me.lkh.hometownleague.user.controller;

import jakarta.servlet.http.HttpServletResponse;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.exception.common.user.NoSuchUserIdException;
import me.lkh.hometownleague.common.exception.common.user.WrongPasswordException;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.session.domain.AuthCheck;
import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.user.domain.JoinDuplicateCheck;
import me.lkh.hometownleague.user.domain.request.JoinRequest;
import me.lkh.hometownleague.user.domain.request.LoginRequest;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.session.domain.UserSession;
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
     * @throws WrongPasswordException 패스워드가 일치하지 않는 경우
     * @throws NoSuchUserIdException ID가 존재하지 않는 경우
     * @throws CommonErrorException 기타 에러가 발생한 경우
     * @see me.lkh.hometownleague.user.service.UserService#loginCheck(User) 
     */
    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequest loginRequest,
                                HttpServletResponse response) throws NoSuchAlgorithmException, WrongPasswordException, NoSuchUserIdException, CommonErrorException {
        User user = new User(loginRequest.getId(), loginRequest.getPassword());

        // 1. ID/PW 체크
        User checkedUser = userService.loginCheck(user);

        // 2. 세션 생성
        UserSession userSession = sessionService.getSession(checkedUser);

        // 3. 세션 저장
        sessionService.login(userSession);

        // 4. 응답쿠키 설정 (SET-COOKIE)
        SessionUtil.setSessionCookie(response, userSession.getSessionId());

        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 회원가입
     * @param joinRequest
     * @return
     * @throws NoSuchAlgorithmException
     * @throws CommonErrorException 기타 에러가 발생한 경우
     */
    @PostMapping("/join")
    public CommonResponse join(@RequestBody JoinRequest joinRequest) throws NoSuchAlgorithmException, CommonErrorException {
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
     * @throws CommonErrorException 기타 에러가 발생한 경우
     */
    @GetMapping("/is-duplicate")
    public CommonResponse isDuplicate(@RequestParam(defaultValue = "id") String type
                                    , @RequestParam String value) throws CommonErrorException{
        boolean isDuplicate = userService.isDuplicate(new JoinDuplicateCheck(type, value));
        return new CommonResponse<>(isDuplicate ? "Y" : "N");
    }

    /**
     * ID로 User정보를 조회
     * @param id 조회하고자 하는 User의 ID
     * @return
     * @throws NoSuchUserIdException 존재하지 않는 ID
     */
//    @AuthCheck
    @GetMapping("/{id}")
    public CommonResponse getUser(@PathVariable("id") String id) throws NoSuchUserIdException{
        User user = userService.selectUserById(id);
        return new CommonResponse<>(user);
    }


}
