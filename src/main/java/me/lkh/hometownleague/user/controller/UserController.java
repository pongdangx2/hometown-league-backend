package me.lkh.hometownleague.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.domain.UserSession;
import me.lkh.hometownleague.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인
     *  1. ID/PW 체크
     *  2. 세션 생성
     *  3. 응답
     * @param user
     * @return
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/login")
    @ResponseBody
    public CommonResponse login(@RequestBody User user, HttpServletRequest request) throws NoSuchAlgorithmException {

        // 1.
        userService.loginCheck(user);

        UserSession userSession = new UserSession(user.getId(), user.getName());
        HttpSession httpSession = request.getSession();

        SessionUtil.setUserSession(httpSession, userSession);

        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }

    /**
     * 회원가입
     * @param user
     * @return
     */
    @PostMapping("/join")
    @ResponseBody
    public CommonResponse join(@RequestBody User user) throws NoSuchAlgorithmException {
        logger.debug("user:" + user);
        userService.join(user);
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }
}
