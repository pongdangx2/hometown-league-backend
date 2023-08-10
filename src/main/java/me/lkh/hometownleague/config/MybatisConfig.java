package me.lkh.hometownleague.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisConfig {

    @Bean(name="htlSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource htldbDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(htldbDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:sql/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
}
