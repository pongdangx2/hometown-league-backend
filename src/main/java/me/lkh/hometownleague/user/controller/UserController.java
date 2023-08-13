package me.lkh.hometownleague.user.controller;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public CommonResponse getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        CommonResponse<User> response = new CommonResponse(user);
        return response;
    }

    /**
     * 회원가입
     * @param user
     * @return
     */
    @PostMapping("/user")
    @ResponseBody
    public CommonResponse join(@RequestBody User user) throws NoSuchAlgorithmException {

        logger.debug("user:" + user);
        userService.join(user);
        return CommonResponse.withEmptyData(ErrorCode.SUCCESS);
    }
}
