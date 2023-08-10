package me.lkh.hometownleague.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HtlDBConfig {

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
