package me.lkh.hometownleague.matching.repository;

import me.lkh.hometownleague.matching.domain.MatchingInfo;
import me.lkh.hometownleague.matching.domain.MatchingListElement;
import me.lkh.hometownleague.matching.domain.response.MatchingDetailBase;
import me.lkh.hometownleague.matching.domain.response.MatchingDetailTeam;
import me.lkh.hometownleague.schedule.matching.domain.MatchingRequestDeleteCheck;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MatchingRepository {

    Integer selectMatchingRequest(Integer teamId);

    Integer selectMatchingInProgress(Integer teamId);

    int insertMatchingRequest(Integer teamId);

    List<MatchingListElement> selectMatching(String userId);

    MatchingDetailBase selectMatchingDetailBase(Integer matchingRequestId);

    MatchingDetailTeam selectMatchingDetailTeam(Integer matchingResultId);

    MatchingRequestDeleteCheck matchingRequestDeleteCheck(Integer matchingRequestId);

    int deleteMatchingRequest(Integer matchingRequestId);

    MatchingInfo selectMatchingInfo(Integer matchingRequestId);

    int updateMatchingInfoToAccept(Integer matchingInfoId);

    int updateMatfingRequestMapping(Map<String, Object> param);
}
