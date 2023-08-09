package me.lkh.hometownleague;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class HometownLeagueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HometownLeagueApplication.class, args);
    }

}
