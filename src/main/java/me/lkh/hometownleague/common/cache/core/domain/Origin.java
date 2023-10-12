package me.lkh.hometownleague.common.cache.core.domain;

import java.util.Optional;

public interface Origin {
    <K, V> Optional<V> getOriginData(K key, Class<V> valueClass);
}
