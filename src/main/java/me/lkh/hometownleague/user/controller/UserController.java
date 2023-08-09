package me.lkh.hometownleague.user.controller;

import me.lkh.hometownleague.user.domain.User;
import me.lkh.hometownleague.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("test")
    public String insertUser(){

        User user = new User("lkh", "이경훈", "P@ssw0rd", "Y");
        int result = userService.insertUser(user);

        return "hello world : " + result;
    }
}
