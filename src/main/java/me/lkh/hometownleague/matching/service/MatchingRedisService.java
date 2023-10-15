package me.lkh.hometownleague.matching.service;

import me.lkh.hometownleague.matching.domain.MatchingQueueElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MatchingRedisService {

    private final RedisTemplate matchingQueueRedisTemplate;

    @Value("${matching.queue.key}")
    private String matchingQueueKey;

    public MatchingRedisService(RedisTemplate matchingQueueRedisTemplate) {
        this.matchingQueueRedisTemplate = matchingQueueRedisTemplate;
    }

    /**
     * 매칭 대기열에 매칭 요청 정보 추가
     * @param matchingQueueElement
     */
    public void makeMatchingRequest(MatchingQueueElement matchingQueueElement){
        ListOperations<String, MatchingQueueElement> listOperations = matchingQueueRedisTemplate.opsForList();
        listOperations.rightPush(matchingQueueKey, matchingQueueElement);
    }

    /**
     * 대기열 가장 앞에 요청정보 추가
     * @param matchingQueueElement
     */
    public void pushLeft(MatchingQueueElement matchingQueueElement){
        ListOperations<String, MatchingQueueElement> listOperations = matchingQueueRedisTemplate.opsForList();
        listOperations.leftPush(matchingQueueKey, matchingQueueElement);
    }

    /**
     * 가장 앞에 있는 원소를 조회
     * @return
     */
    public MatchingQueueElement getLeft(){
        ListOperations<String, MatchingQueueElement> listOperations = matchingQueueRedisTemplate.opsForList();
        return listOperations.index(matchingQueueKey, 0L);
    }

    /**
     * 가장 앞에 있는 원소를 조회하고 제거
     * @return
     */
    public MatchingQueueElement popLeft(){
        ListOperations<String, MatchingQueueElement> listOperations = matchingQueueRedisTemplate.opsForList();
        return listOperations.leftPop(matchingQueueKey);
    }
}
