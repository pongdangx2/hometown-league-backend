package me.lkh.hometownleague.schedule.matching.service.location;

import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingBaseInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingLocation;

import java.util.List;

/**
 * 경기 장소 기준으로 필터링할 기준을 쉽게 변경하기 위함
 * 현재 다음 두가지 기준 중 고민중
 *  1) 좌표 기준
 *  2) 법정동코드 기준
 */
public interface LocationFilteringStrategy {
    List<TeamMatchingBaseInfo> doFilter(List<TeamMatchingLocation> myTeamMatchingLocationList, List<TeamMatchingBaseInfo> teamMatchingBaseInfoList);

}
