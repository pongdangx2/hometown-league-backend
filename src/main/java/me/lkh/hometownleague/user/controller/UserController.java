package me.lkh.hometownleague.user.controller;

import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.service.UserService;
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
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }
}
