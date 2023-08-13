package me.lkh.hometownleague.user.controller;

import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.service.Impl.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

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
}
