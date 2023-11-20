package me.lkh.hometownleague.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Value("${image.team.path}")
    private String imageTeamPath;

    @Value("${image.user.path}")
    private String imageUserPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image-team/**")
                .addResourceLocations("file:" + imageTeamPath +"/")
                .setCachePeriod(20);
        registry.addResourceHandler("/image-user/**")
                .addResourceLocations("file:"+ imageUserPath + "/")
                .setCachePeriod(20);
    }
}
