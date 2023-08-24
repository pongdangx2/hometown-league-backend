package me.lkh.hometownleague.session.service;

import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.user.domain.User;

public interface SessionService {

    UserSession getSession(User user);

    String makeSessionKey(UserSession userSession);

    /**
     * 이미 존재하는 세션인지 여부 리턴
     * @param sessionId
     * @return
     */
    boolean isExistSession(String sessionId);

    /**
     * 신규 생성한 세션정보 저장
     * @param userSession
     */
    void login(UserSession userSession);

    /**
     * 최근 접속시간 업데이트
     * @param userSession
     */
    void updateLastAccessedTime(UserSession userSession);
}
