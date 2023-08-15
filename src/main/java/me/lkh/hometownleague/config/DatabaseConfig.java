package me.lkh.hometownleague.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    /**
     * Hikari CP 의 장점
     *  - 적은 메모리 사용량
     *  - 높은 처리량
     *  - 풍부한 구성 옵션
     *  - 스레드 안정성
     *
     * --> 빠르고 간단하며 믿을수 있는 CP
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.hikari.htldb")
    public HikariConfig htldbConfig(){
        return new HikariConfig();
    }

    @Bean
    public DataSource htldbDataSource() {
        return new HikariDataSource(htldbConfig());
    }
}
