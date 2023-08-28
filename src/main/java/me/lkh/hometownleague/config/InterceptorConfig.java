package me.lkh.hometownleague.config;

import me.lkh.hometownleague.common.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final SessionInterceptor sessionInterceptor;

    public InterceptorConfig(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 세션 처리를 위한 인터셉터 등록
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
// 2023.08.28 애노테이션을 이용해 체크하는 방식으로 수정
//                .excludePathPatterns("/user/**") // 로그인/회원가입 관련 요청 시 세션체크 제외
                ;
    }
}
