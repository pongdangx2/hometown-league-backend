package me.lkh.hometownleague.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SessionUtil {

    public static final String SESSION_ID = "SESSION_ID";

    /**
     * Session ID 생성 전략
     *  UserID + yyyyMMddHHmmss 형태의 문자열의 해시값(SHA-256)
     * @param userId
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSessionId(String userId) throws NoSuchAlgorithmException {
        String curLocalDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return SecurityUtil.hashEncrypt(userId + curLocalDateTime);
    }

    private static String getSessionString(String sessionId){
        StringBuffer stringBuffer = new StringBuffer("SESSION=")
                .append(sessionId)
                .append(";")
                .append("Path=/;") // 모든 URL에 지정
                .append("HttpOnly;")    // http통신에서만 접근하도록.(javascript를 통한 접근을 금지시켜 xss를 방지할 수 있음)
                .append("SameSite=Lax"); // A cookie with "SameSite=Lax" will be sent with a same-site request, or a cross-site top-level navigation with a "safe" HTTP method
        return stringBuffer.toString();
    }

    public static void setSessionCookie(HttpServletResponse response, String sessionId){
        response.setHeader("SET-COOKIE", getSessionString(sessionId));
    }

    /**
     * httpServletRequest에서 SessionId를 꺼내준다.
     * @param httpServletRequest
     * @return
     */
    public static Optional<String> getSessionIdFromRequest(HttpServletRequest httpServletRequest){
        final StringBuffer stringBuffer = new StringBuffer();
        Optional.ofNullable(httpServletRequest.getHeader("cookie")).ifPresent(
                cookie -> stringBuffer.append(cookie.split("=")[1])
        );
        return stringBuffer.length() == 0 ? Optional.empty() : Optional.of(stringBuffer.toString());
    }
}
