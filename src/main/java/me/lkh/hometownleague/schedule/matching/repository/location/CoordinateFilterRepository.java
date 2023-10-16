package me.lkh.hometownleague.schedule.matching.repository.location;

import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingBaseInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingLocation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoordinateFilterRepository {

    List<TeamMatchingLocation> selectHometownList(List<TeamMatchingBaseInfo> teamMatchingBaseInfoList);

}
