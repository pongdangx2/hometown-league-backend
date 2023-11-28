package me.lkh.hometownleague.rank.repository;

import me.lkh.hometownleague.rank.domain.MemberCount;
import me.lkh.hometownleague.rank.domain.Ranking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface RankRepository {

    List<Ranking> selectRankingList(Integer numOfTeam);

    List<MemberCount> selectMemberCount(Set<Integer> teamIdSet);
}
