package me.lkh.hometownleague.common.util;

import jakarta.servlet.http.HttpSession;
import me.lkh.hometownleague.user.domain.UserSession;

public class SessionUtil {

    private static final String USER_SESSION = "USER_SESSION";

    public static void setUserSession(HttpSession httpSession, UserSession userSession){
        httpSession.setAttribute(USER_SESSION, userSession);
    }

    public static UserSession getUserSession(HttpSession httpSession){
        return (UserSession)httpSession.getAttribute(USER_SESSION);
    }
}
