package me.lkh.hometownleague.common.common.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommonRepository {
    List<Map<String, String>> selectCommonCode(String groupId);
}
