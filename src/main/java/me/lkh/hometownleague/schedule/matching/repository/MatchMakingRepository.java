package me.lkh.hometownleague.schedule.matching.repository;

import me.lkh.hometownleague.schedule.matching.domain.MatchingRequestInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingBaseInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchMakingRepository {

    MatchingRequestInfo selectMatchingRequestInfo(Integer matchingRequestId);

    int updateProcessYn(Integer matchingRequestId);

    int updateProcessTimestamp(Integer matchingRequestId);

    List<TeamMatchingBaseInfo> selectTeamMatchingBaseInfo(Integer matchingRequestId, Integer myScore, Integer scoreMaxDiff);

}