package me.lkh.hometownleague.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lkh.hometownleague.common.cache.core.CacheManager;
import me.lkh.hometownleague.common.cache.core.domain.Origin;
import me.lkh.hometownleague.common.cache.repository.CacheRepository;
import me.lkh.hometownleague.domain.CommonCode;
import me.lkh.hometownleague.domain.CommonCodeKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CacheTest {

    @Autowired
    RedisTemplate<String, String> redisStringTemplate;

    @Autowired
    CacheRepository cacheRepository;

    @DisplayName("공통코드 캐시 테스트 (Miss)")
    @Transactional
    @Test
    void 공통코드조회_캐시_Miss() throws JsonProcessingException {

        // 1. DB에 코드 삽입
        CacheManager<CommonCodeKey, CommonCode> cacheManager = new CacheManager<>();
        CommonCodeKey commonCodeKey = new CommonCodeKey("9999", "9999");
        CommonCode commonCode = new CommonCode("9999", "9999", "test");
        cacheRepository.insertCommonCode(commonCode);

        // 2. Redis에 데이터 삭제
        ValueOperations<String, String> stringValueOperations = redisStringTemplate.opsForValue();
        ObjectMapper objectMapper = new ObjectMapper();
        stringValueOperations.getAndDelete(objectMapper.writeValueAsString(commonCodeKey));

        // 3. 캐시 데이터 조회 (Look aside cache)
        Optional<CommonCode> commonCodeResult = cacheManager.getDataWithRedisCache(commonCodeKey, CommonCode.class, new Origin() {
            @Override
            public <K, V> Optional<V> getOriginData(K key, Class<V> valueClass) {
                return Optional.ofNullable((V)cacheRepository.getCommonCode((CommonCodeKey)key));
            }
        }, redisStringTemplate);

        // 4.조회된 값 테스트
        assertThat(commonCodeResult).isNotEmpty();
        assertThat(commonCodeResult.get().getCode()).isEqualTo(commonCode.getCode());
        assertThat(commonCodeResult.get().getCodeName()).isEqualTo(commonCode.getCodeName());
        assertThat(commonCodeResult.get().getGroupId()).isEqualTo(commonCode.getGroupId());

        // 5. 캐시 저장 여부 테스트
        assertThat(redisStringTemplate.hasKey(objectMapper.writeValueAsString(commonCodeKey))).isTrue();

        // 6. 캐시 데이터 삭제
        stringValueOperations.getAndDelete(objectMapper.writeValueAsString(commonCodeKey));

        // 7. DB 데이터 삭제
        cacheRepository.deleteCommonCode(commonCodeKey);
    }

    @DisplayName("공통코드 캐시 테스트 (Hit)")
    @Transactional
    @Test
    void 공통코드조회_캐시_Hit() throws JsonProcessingException {

        // 1. 코드 생성
        CacheManager<CommonCodeKey, CommonCode> cacheManager = new CacheManager<>();
        CommonCodeKey commonCodeKey = new CommonCodeKey("9999", "9999");
        CommonCode commonCode = new CommonCode("9999", "9999", "test");

        // 2. 캐시에 데이터 삽입
        ValueOperations<String, String> stringValueOperations = redisStringTemplate.opsForValue();
        ObjectMapper objectMapper = new ObjectMapper();
        stringValueOperations.set(objectMapper.writeValueAsString(commonCodeKey), objectMapper.writeValueAsString(commonCode));


        // 3. 캐시 데이터 조회 (Look aside cache)
        Optional<CommonCode> commonCodeResult = cacheManager.getDataWithRedisCache(commonCodeKey, CommonCode.class, new Origin() {
            @Override
            public <K, V> Optional<V> getOriginData(K key, Class<V> valueClass) {
                return Optional.ofNullable((V)cacheRepository.getCommonCode((CommonCodeKey)key));
            }
        }, redisStringTemplate);

        // 4.조회된 값 테스트
        assertThat(commonCodeResult).isNotEmpty();
        assertThat(commonCodeResult.get().getCode()).isEqualTo(commonCode.getCode());
        assertThat(commonCodeResult.get().getCodeName()).isEqualTo(commonCode.getCodeName());
        assertThat(commonCodeResult.get().getGroupId()).isEqualTo(commonCode.getGroupId());

        // 5. 캐시 저장 여부 테스트
        assertThat(redisStringTemplate.hasKey(objectMapper.writeValueAsString(commonCodeKey))).isTrue();

        // 6. 캐시 데이터 삭제
        stringValueOperations.getAndDelete(objectMapper.writeValueAsString(commonCodeKey));
    }

}
