package me.lkh.hometownleague.schedule.matching.service.location.impl;

import me.lkh.hometownleague.common.util.LocationUtil;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingBaseInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingLocation;
import me.lkh.hometownleague.schedule.matching.repository.location.CoordinateFilterRepository;
import me.lkh.hometownleague.schedule.matching.service.location.LocationFilteringStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Qualifier("coordinateFilter")
@Component
public class CoordinateFilter implements LocationFilteringStrategy {

    private final CoordinateFilterRepository coordinateFilterRepository;

    // 필터링할 거리차이
    private final int distanceDiff = 25;

    public CoordinateFilter(CoordinateFilterRepository coordinateFilterRepository) {
        this.coordinateFilterRepository = coordinateFilterRepository;
    }

    /**
     *
     * @param myTeamMatchingLocationList 내팀의 경기 장소 목록
     * @param teamMatchingBaseInfoList 점수로 1차 필터링된 팀의 목록
     * @return
     */
    @Override
    public List<TeamMatchingBaseInfo> doFilter(List<TeamMatchingLocation> myTeamMatchingLocationList, List<TeamMatchingBaseInfo> teamMatchingBaseInfoList) {

        Map<Integer, Double> countMap = new HashMap<>();
        // 1차필터링한 팀의 모든 경기 장소를 순회
        coordinateFilterRepository.selectHometownList(teamMatchingBaseInfoList).forEach(teamMatchingLocation -> {
            for (TeamMatchingLocation myTeamMatchingLocation : myTeamMatchingLocationList) {
                // 이미 포함되어 있지 않은 경우에만 체크
                if(!countMap.containsKey(myTeamMatchingLocation.getTeamId())){
                    // 최대 거리차이보다 거리 차이가 작으면 해시맵에 추가
                    double distance = LocationUtil.getDistance(myTeamMatchingLocation.getLatitude(), myTeamMatchingLocation.getLongitude(), teamMatchingLocation.getLatitude(), teamMatchingLocation.getLongitude());
                    if (distanceDiff >= distance) {
//                        countMap.put(myTeamMatchingLocation.getTeamId(), distance);
                        countMap.put(teamMatchingLocation.getTeamId(), distance);
                    }
                }
            }
        });

        return teamMatchingBaseInfoList.stream().filter(teamMatchingBaseInfo -> countMap.containsKey(teamMatchingBaseInfo.getTeamId())).toList();
    }

    @Override
    public TeamMatchingLocation getMatchingLocation(List<TeamMatchingLocation> myTeamMatchingLocationList, List<TeamMatchingLocation> otherTeamMatchingLocationList) {

        for (TeamMatchingLocation myTeamMatchingLocation : myTeamMatchingLocationList) {
            for(TeamMatchingLocation otherTeamMatchingLocation : otherTeamMatchingLocationList) {
                // 최대 거리차이보다 거리 차이가 작으면 리턴
                double distance = LocationUtil.getDistance(myTeamMatchingLocation.getLatitude(), myTeamMatchingLocation.getLongitude(), otherTeamMatchingLocation.getLatitude(), otherTeamMatchingLocation.getLongitude());
                if (distanceDiff >= distance) {
                    return myTeamMatchingLocation;
                }
            }
        }

        return null;
    }
}
