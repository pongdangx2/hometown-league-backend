package me.lkh.hometownleague.session.service.impl;

import me.lkh.hometownleague.session.service.SessionService;
import me.lkh.hometownleague.common.util.SessionUtil;
import me.lkh.hometownleague.session.domain.UserSession;
import me.lkh.hometownleague.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * "Redis" 에 세션정보를 저장하고 관리하는 SessionService 구현체
 * @author leekh
 * @see me.lkh.hometownleague.session.service.SessionService
 */
@Service
public class RedisSessionService implements SessionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RedisTemplate sessionRedisTemplate;

    private final RedisTemplate redisStringTemplate;

    @Value("${spring.session.namespace}")
    private String redisSessionPrefix;

    @Value("${spring.session.expire-minute}")
    private Integer redisSessionExpirationTimeInMinute;

    private final String USER_SESSION = "USER_SESSION";
    private final String LAST_ACCESSED_TIME = "lastAccessedTime";

    public RedisSessionService(RedisTemplate sessionRedisTemplate, RedisTemplate redisStringTemplate) {
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.redisStringTemplate = redisStringTemplate;
    }

    /**
     * User정보를 통해 UserSession정보를 생성
     * @param user
     * @return 생성된 UserSession
     */
    @Override
    public UserSession getSession(User user) {
        UserSession userSession = null;
        try{
            userSession = new UserSession(redisSessionPrefix + SessionUtil.getSessionId(user.getId())
                    , user.getId()
                    , user.getNickname());
        } catch(NoSuchAlgorithmException noSuchAlgorithmException){
            logger.error("Cannot get Session ID");
        }

        return userSession;
    }

    /**
     * Redis에 세션정보 존재 여부를 반환
     * @param sessionId
     * @return
     */
    @Override
    public boolean isExistSession(String sessionId){
        return sessionRedisTemplate.hasKey(sessionId);
    }

    /**
     * 신규 생성한 세션정보 저장
     * @param userSession
     */
    @Override
    public void login(UserSession userSession){
        // 1. UserSession 저장
        HashOperations<String, String, UserSession> userSessionHashOperations = sessionRedisTemplate.opsForHash();
        userSessionHashOperations.put(userSession.getSessionId(), USER_SESSION, userSession);

        // 2. 최근 접속 시간 저장
        updateLastAccessedTime(userSession);
    }

    /**
     * 1. 최근 접속시간 업데이트
     * 2. 세션만료시간 초기화
     * @param userSession
     */
    @Override
    public void updateLastAccessedTime(UserSession userSession){
        HashOperations<String, String, String> hashOperations = redisStringTemplate.opsForHash();
        hashOperations.put(userSession.getSessionId(), LAST_ACCESSED_TIME, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HHmmss")));

        redisStringTemplate.expire(userSession.getSessionId(), redisSessionExpirationTimeInMinute, TimeUnit.MINUTES);
    }

    /**
     * 세션ID로 UserSesison 얻기 (Session Repository로부터)
     * @param userSessionId
     * @return
     */
    @Override
    public UserSession getUserSession(String userSessionId) {

        HashOperations<String, String, UserSession> userSessionHashOperations = sessionRedisTemplate.opsForHash();
        UserSession userSession = userSessionHashOperations.get(userSessionId, USER_SESSION);

        return userSession;
    }
}
