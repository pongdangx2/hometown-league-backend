package me.lkh.hometownleague.common.cache.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CacheRepository {
    Map<String, Object> getValue(String query);
}
