package me.lkh.hometownleague.matching.repository;

import me.lkh.hometownleague.matching.domain.MatchingListElement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchingRepository {

    Integer selectMatchingRequest(Integer teamId);

    Integer selectMatchingInProgress(Integer teamId);

    int insertMatchingRequest(Integer teamId);

    List<MatchingListElement> selectMatching(String userId);

}
