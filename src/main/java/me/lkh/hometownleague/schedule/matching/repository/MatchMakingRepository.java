package me.lkh.hometownleague.schedule.matching.repository;

import me.lkh.hometownleague.schedule.matching.domain.MatchingRequestInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingBaseInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingLocation;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchMakingRepository {

    MatchingRequestInfo selectMatchingRequestInfo(Integer matchingRequestId);

    int updateProcessYn(Integer matchingRequestId);

    int updateProcessTimestamp(Integer matchingRequestId);

    List<TeamMatchingBaseInfo> selectTeamMatchingBaseInfo(Integer matchingRequestId, Integer myScore, Integer scoreMaxDiff);

    List<TeamMatchingLocation>  selectMyTeamMatchingLocation(Integer teamId);

    List<TeamMatchingTime>  selectMyTeamMatchingTime(Integer teamId);

    List<TeamMatchingTime> selectPlayTimeList(List<TeamMatchingBaseInfo> teamMatchingBaseInfoList);

}