package me.lkh.hometownleague;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 프론트엔드 개발자에게 문서를 쉽게 공유하기 위해 만든 Controller
 */
@Controller
@RequestMapping("/rest")
public class DocController {

    /**
     * Rest Docs로 만든 api 명세서를 응답한다.
     * @return /src/main/resources/templates/HometownLeagueApiDoc.html
     */
    @RequestMapping ("/docs.do")
    public String doc(){
        String test = "123";
        System.out.println(test);
        return "HometownLeagueApiDoc";
    }
}
