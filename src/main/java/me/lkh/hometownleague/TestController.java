package me.lkh.hometownleague;

import me.lkh.hometownleague.session.domain.AuthCheck;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @AuthCheck
    @PostMapping("/tt")
    public String test(){
        String test = "ttt";

        test = "test Response";
        return test;
    }
}
