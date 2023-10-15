package me.lkh.hometownleague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HometownLeagueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HometownLeagueApplication.class, args);
    }

}
