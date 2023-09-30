package me.lkh.hometownleague.matching.service;

import me.lkh.hometownleague.matching.domain.MatchingQueueElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
