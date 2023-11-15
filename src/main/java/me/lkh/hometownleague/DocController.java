package me.lkh.hometownleague;

import me.lkh.hometownleague.schedule.matching.service.MatchMakingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 프론트엔드 개발자에게 문서를 쉽게 공유하기 위해 만든 Controller
 */
@Controller
@RequestMapping("/rest")
public class DocController {

    private final MatchMakingService matchMakingService;

    public DocController(MatchMakingService matchMakingService) {
        this.matchMakingService = matchMakingService;
    }

    /**
     * Rest Docs로 만든 api 명세서를 응답한다.
     * @return /src/main/resources/templates/HometownLeagueApiDoc.html
     */
    @RequestMapping ("/docs.do")
    public String doc(){
        return "HometownLeagueApiDoc";
    }

    /**
     * 매칭 배치
     * @return
     */
    @PostMapping("/batch")
    public Map<String, String> matchingBatch(){

        matchMakingService.matchMakingJob();

        Map<String, String> result = new HashMap<>();
        result.put("CODE", "SUCCESS");
        return result;
    }
}
