package me.lkh.hometownleague.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.lkh.hometownleague.common.exception.common.user.InvalidSessionException;
import me.lkh.hometownleague.common.exception.common.user.UnauthorizedException;
import me.lkh.hometownleague.session.domain.AuthCheck;
import me.lkh.hometownleague.session.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Optional;


/**
 * 세션 처리 인터셉터.
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;

    public SessionInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // AuthCheck 애노테이션이 없는 경우 true 리턴
        if(!isAuthCheckTarget(handler))
            return true;

        logger.debug("interceptor request url:" + request.getRequestURL());
        // session 조회
        Optional<String> optionalSession = Optional.ofNullable(request.getHeader("cookie"));
        optionalSession.ifPresentOrElse(cookie -> {
            String sessionId = cookie.split("=")[1];
            // 세션이 없는 경우
            if(!sessionService.isExistSession(sessionId))
                throw new InvalidSessionException();
        }
            //세션이 없는 경우
            , () -> {throw new UnauthorizedException(); });

        return true;
    }

    /*
    * handler : URL을 처리할 handler 정보
    Request -> dispatcherservlet -> HandlerMapping [URL과 매핑되는 Controller 탐색]
                                 <-
                                 -> HandlerAdaptor [Controller의 비즈니스로직 수행 위임]
     */
    private boolean isAuthCheckTarget(Object handler) {

        if(handler instanceof ResourceHttpRequestHandler)
            return false;

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //AuthCheck anntotation이 있는 경우
        if (null != handlerMethod.getMethodAnnotation(AuthCheck.class)
                || null != handlerMethod.getBeanType().getAnnotation(AuthCheck.class)) {
            return true;
        }

        //annotation이 없는 경우
        return false;
    }

}
