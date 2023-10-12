package me.lkh.hometownleague;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.cache.core.CacheManager;
import me.lkh.hometownleague.common.cache.core.domain.Cache;
import me.lkh.hometownleague.common.cache.core.domain.Origin;
import me.lkh.hometownleague.common.cache.repository.CacheRepository;
import me.lkh.hometownleague.domain.CommonCode;
import me.lkh.hometownleague.domain.CommonCodeKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 캐시 테스트 용으로 만든 클래스
 */
@RestController
@RequestMapping("/cache")
public class CacheTestController {

    private final RedisTemplate<String, String> redisStringTemplate;
    private final CacheRepository cacheRepository;

    public CacheTestController(RedisTemplate<String, String> redisStringTemplate, CacheRepository cacheRepository) {
        this.redisStringTemplate = redisStringTemplate;
        this.cacheRepository = cacheRepository;
    }

    @RequestMapping("/test")
    @ResponseBody
    public CommonCode cacheTest(){
        CacheManager<CommonCodeKey, CommonCode> cacheManager = new CacheManager<>();
        CommonCodeKey commonCodeKey = new CommonCodeKey("0000", "O");

        Optional<CommonCode> commonCodeResult = cacheManager.getData(commonCodeKey, CommonCode.class, new Cache() {
            @Override
            public <K, V> Optional<V> checkCache(K key, Class<V> valueClass) {

                Optional<V> result;
                ObjectMapper objectMapper = new ObjectMapper();
                String stringKey;
                try {
                    stringKey = objectMapper.writeValueAsString(key);
                } catch(JsonProcessingException jsonProcessingException){
                    return Optional.empty();
                }

                if(redisStringTemplate.hasKey(stringKey)){
                    ValueOperations<String, String> stringValueOperations = redisStringTemplate.opsForValue();
                    String stringResult = stringValueOperations.get(stringKey);
                    try {
                        result = Optional.ofNullable(objectMapper.readValue(stringResult, valueClass));
                    } catch(JsonProcessingException jsonProcessingException){
                        return Optional.empty();
                    }
                } else {
                    result = Optional.empty();
                }
                return result;
            }

            @Override
            public <K, V> boolean setCache(K key, V value) {
                ObjectMapper objectMapper = new ObjectMapper();

                ValueOperations<String, String> stringValueOperations = redisStringTemplate.opsForValue();
                try {
                    stringValueOperations.set(objectMapper.writeValueAsString(key), objectMapper.writeValueAsString(value));
                } catch(JsonProcessingException jsonProcessingException){
                    return false;
                }
                return true;
            }
        }, new Origin() {
            @Override
            public <K, V> Optional<V> getOriginData(K key, Class<V> valueClass) {
                Optional<V> result = Optional.ofNullable((V)cacheRepository.getCommonCode((CommonCodeKey)key));
                return result;
            }
        });

        return commonCodeResult.get();
    }
}
