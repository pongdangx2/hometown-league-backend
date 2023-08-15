package me.lkh.hometownleague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)    // 세션은 1시간 유지
public class HometownLeagueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HometownLeagueApplication.class, args);
    }

}
