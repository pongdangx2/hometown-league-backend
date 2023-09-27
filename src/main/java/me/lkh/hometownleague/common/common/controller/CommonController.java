package me.lkh.hometownleague.common.common.controller;

import me.lkh.hometownleague.common.common.service.CommonService;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.session.domain.AuthCheck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AuthCheck
@RequestMapping("/common")
public class CommonController {

    private final CommonService commonService;

    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @GetMapping("/code/{groupId}")
    public CommonResponse selectCommonCode(@PathVariable("groupId") String groupId){
        return new CommonResponse<>(commonService.selectCommonCode(groupId));
    }
}
