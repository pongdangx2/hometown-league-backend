package me.lkh.hometownleague.session.service;

import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.user.domain.User;

public interface SessionService {

    /**
     * User를 통해 UserSession을 생성
     * @param user Session을 생성할 User
     * @return 생성된 UserSession
     */
    UserSession getSession(User user);

    /**
     * 저장소에 존재하는 세션인지 여부 리턴
     * @param sessionId
     * @return true: 존재 / false: 미존재
     */
    boolean isExistSession(String sessionId);

    /**
     * 신규 생성한 세션정보를 저장소에 저장
     * @param userSession
     */
    void login(UserSession userSession);

    /**
     * 최근 접속시간 업데이트
     * @param userSession
     */
    void updateLastAccessedTime(UserSession userSession);

    /**
     * 세션 저장소로부터 UserSession 얻어오기
     * @param userSessionId
     * @return
     */
    UserSession getUserSession(String userSessionId);

    /**
     * 세션 저장소로부터 세션 정보 삭제
     * @param userSessionId
     */
    void deleteUserSession(String userSessionId);

}
