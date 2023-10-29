package me.lkh.hometownleague.common.cache.core;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.cache.core.domain.Origin;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;

public class CacheManager<K, V> {

    /**
     * Redis 캐시 모듈(Look aside cache)
     * @param key
     * @param valueClass
     * @param origin
     * @param redisTemplate
     * @return
     * @throws JsonProcessingException
     */
    public Optional<V> getDataWithRedisCache(K key, Class<V> valueClass, Origin origin, RedisTemplate redisTemplate) throws JsonProcessingException {

        Optional<V> cacheResult = getRedisCacheData(key, valueClass, redisTemplate);
        // Cache Hit
        if(cacheResult.isPresent()){
            return cacheResult;
        }
        // Cache Miss
        else {
            Optional<V> originData = origin.getOriginData(key, valueClass);
            // 원본 데이터 미존재
            if(originData.isEmpty()){
                return Optional.empty();
            } else {

                // 캐시에 데이터 추가
                if(setRedisCacheData(key, originData.get(), redisTemplate))
                    return originData;
                // 캐시에 데이터 추가 실패
                else
                    return Optional.empty();
            }
        }
    }

    /**
     * Redis 캐시에 데이터가 있는지 확인
     * @param key
     * @param valueClass
     * @param redisTemplate
     * @return
     * @throws JsonProcessingException
     */
    private Optional<V> getRedisCacheData(K key, Class<V> valueClass, RedisTemplate redisTemplate) throws JsonProcessingException {
        Optional<V> result;
        ObjectMapper objectMapper = new ObjectMapper();
        String stringKey;
        try {
            stringKey = objectMapper.writeValueAsString(key);
        } catch(JsonProcessingException jsonProcessingException){
            return Optional.empty();
        }

        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        Optional<String> optionalStringResult = Optional.ofNullable(stringValueOperations.get(stringKey));

        // Cache miss
        if(optionalStringResult.isEmpty()){
            result = Optional.empty();
        }
        // Cahce hit
        else {
            result = Optional.ofNullable(objectMapper.readValue(optionalStringResult.get(), valueClass));
        }

        return result;
    }

    /**
     * Redis 캐시에 데이터 저장
     * @param key
     * @param value
     * @param redisTemplate
     * @return
     */
    private boolean setRedisCacheData(K key, V value, RedisTemplate redisTemplate){
        ObjectMapper objectMapper = new ObjectMapper();

        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        try {
            stringValueOperations.set(objectMapper.writeValueAsString(key), objectMapper.writeValueAsString(value));
        } catch(JsonProcessingException jsonProcessingException){
            return false;
        }
        return true;
    }
}
