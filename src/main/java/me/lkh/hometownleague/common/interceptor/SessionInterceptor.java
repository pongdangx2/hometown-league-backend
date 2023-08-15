package me.lkh.hometownleague.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.lkh.hometownleague.common.exception.common.user.InvalidSessionException;
import me.lkh.hometownleague.common.exception.common.user.UnauthorizedException;
import me.lkh.hometownleague.common.util.SessionUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * 세션 처리 인터셉터.
 *
 */
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 세션 획득 (세션이 없는 경우 null)
        Optional.ofNullable(request.getSession(false))
                .ifPresentOrElse(session -> {
                            // 세션에 UserSession이 없는 경우
                            if(Optional.ofNullable(SessionUtil.getUserSession(session)).isEmpty())
                                throw new InvalidSessionException();
                        }
                        // 세션이 없는 경우
                        ,() -> { throw new UnauthorizedException(); });

        return true;
    }
}
