package me.lkh.hometownleague.matching.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchingRepository {

    Integer selectMatchingRequest(Integer teamId);

    Integer selectMatchingInProgress(Integer teamId);

    int insertMatchingRequest(Integer teamId);
}
