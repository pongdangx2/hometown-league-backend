package me.lkh.hometownleague.common.cache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.cache.repository.CacheRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheService {
    private final RedisTemplate<String, String> cacheRedisTemplate;

    private final CacheRepository cacheRepository;

    private final ObjectMapper objectMapper;

    public CacheService(RedisTemplate<String, String> cacheRedisTemplate, CacheRepository cacheRepository) {
        this.cacheRedisTemplate = cacheRedisTemplate;
        this.cacheRepository = cacheRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * redis cache에서만 데이터를 조회
     * @param key
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Optional<V> getValueFromRedis(K key, Class<V> valueClass) throws JsonProcessingException {
        if(!hasKey(key)){
            return Optional.empty();
        }
        ValueOperations<String, String> stringStringValueOperations = cacheRedisTemplate.opsForValue();
        String result = stringStringValueOperations.get(getJsonString(key));
        return Optional.ofNullable(objectMapper.readValue(result, valueClass));
    }

    /**
     * Redis에 데이터가 있으면 반환하고, 없으면 DB에서 조회하여 Redis에 삽입한 뒤 반환
     * @param key
     * @param valueClass
     * @param query DB에서 데이터를 조회하는 쿼리 (조회 시 컬럼Alias는 valueClass의 필드명과 같아야함.)
     * @param <K>
     * @param <V>
     * @return
     * @throws JsonProcessingException
     */
    public <K, V> V getValue(K key, Class<V> valueClass, String query) throws JsonProcessingException {
        String result = "";
        if(hasKey(key)){
            ValueOperations<String, String> stringStringValueOperations = cacheRedisTemplate.opsForValue();
            result = stringStringValueOperations.get(getJsonString(key));
        } else {
            V value = objectMapper.convertValue(cacheRepository.getValue(query), valueClass);
            setValue(key, value);
            return value;
        }
        return objectMapper.readValue(result, valueClass);
    }

    public <K, V> void setValue(K key, V value) throws JsonProcessingException {
        ValueOperations<String, String> stringStringValueOperations = cacheRedisTemplate.opsForValue();
        stringStringValueOperations.set(getJsonString(key), getJsonString(value));
    }

    private <K> boolean hasKey(K key) throws JsonProcessingException {
        return Boolean.TRUE.equals(cacheRedisTemplate.hasKey(getJsonString(key)));
    }
    private <T> String getJsonString(T value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}
