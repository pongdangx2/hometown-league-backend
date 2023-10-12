package me.lkh.hometownleague.common.cache.core.domain;

import java.util.Optional;

public interface Cache{
    /**
     * 캐시에서 데이터를 조회
     * @param key
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    <K, V> Optional<V> checkCache(K key, Class<V> valueClass);

    /**
     * 캐시 miss시 캐시에 데이터를 저장
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     * @return 캐시에 데이터 저장 성공 여부
     */
    <K, V> boolean setCache(K key, V value);
}
