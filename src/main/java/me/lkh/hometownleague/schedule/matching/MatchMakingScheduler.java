package me.lkh.hometownleague.schedule.matching;

import me.lkh.hometownleague.schedule.matching.service.MatchMakingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MatchMakingScheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${matching.use-yn}")
    private String useYn;

    private final MatchMakingService matchMakingService;

    public MatchMakingScheduler(MatchMakingService matchMakingService) {
        this.matchMakingService = matchMakingService;
    }

    @Scheduled(fixedRate = 1000) // 1초마다 실행 (앞의 작업이 끝나야 수행)
    public void run(){
        if("Y".equals(useYn))
            matchMakingService.matchMakingJob();
    }
}
