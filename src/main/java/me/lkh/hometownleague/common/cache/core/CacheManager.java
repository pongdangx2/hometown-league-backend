package me.lkh.hometownleague.common.cache.core;


import me.lkh.hometownleague.common.cache.core.domain.Cache;
import me.lkh.hometownleague.common.cache.core.domain.Origin;
import me.lkh.hometownleague.common.cache.core.exception.CacheInsertFailedException;

import java.util.Optional;

public class CacheManager<K, V> {

    public Optional<V> getData(K key, Class<V> valueClass, Cache cache, Origin origin){
        // 캐시에서 먼저 체크
        Optional<V> result = cache.checkCache(key, valueClass);

        // 캐시 Miss
        if(result.isEmpty()){
            // 원본 조회
            result = origin.getOriginData(key, valueClass);
            // 캐시 세팅
            result.ifPresent(value -> {
                if(!cache.setCache(key, value)){
                    throw new CacheInsertFailedException();
                }
            });
        }
        return result;
    }
}
