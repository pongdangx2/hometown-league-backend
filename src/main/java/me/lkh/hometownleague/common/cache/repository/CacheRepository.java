package me.lkh.hometownleague.common.cache.repository;

import me.lkh.hometownleague.domain.CommonCode;
import me.lkh.hometownleague.domain.CommonCodeKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CacheRepository {
    Map<String, Object> getValue(String query);

    CommonCode getCommonCode(CommonCodeKey commonCodeKey);

    int insertCommonCode(CommonCode commonCode);

    int deleteCommonCode(CommonCodeKey commonCodeKey);

}
