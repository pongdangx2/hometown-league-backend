package me.lkh.hometownleague.schedule.matching.repository;

import me.lkh.hometownleague.schedule.matching.domain.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchMakingRepository {

    MatchingRequestInfo selectMatchingRequestInfo(Integer matchingRequestId);

    int updateProcessYn(Integer matchingRequestId);

    int updateProcessTimestamp(Integer matchingRequestId);

    int updateRequestInfo(Integer matchingRequestId);

    List<TeamMatchingBaseInfo> selectTeamMatchingBaseInfo(Integer matchingRequestId, Integer myScore, Integer scoreMaxDiff);

    List<TeamMatchingLocation>  selectMyTeamMatchingLocation(Integer teamId);

    List<TeamMatchingTime>  selectTeamMatchingTime(Integer teamId);

    List<TeamMatchingTime> selectPlayTimeList(List<TeamMatchingBaseInfo> teamMatchingBaseInfoList);

    int insertTeamMatchingRequestMapping(TeamMatchingRequestMapping teamMatchingRequestMapping);

    int insertMatchingInfo(Integer matchingRequestId);
}