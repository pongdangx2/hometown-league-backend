package me.lkh.hometownleague.rank.service;

import me.lkh.hometownleague.rank.WinResult;
import me.lkh.hometownleague.rank.domain.CalculatedScore;
import me.lkh.hometownleague.rank.domain.MemberCount;
import me.lkh.hometownleague.rank.domain.Ranking;
import me.lkh.hometownleague.rank.repository.RankRepository;
import me.lkh.hometownleague.team.domain.Team;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 랭크를 어떻게 정할지에 대한 로직을 가진 클래스.
 */
@Service
public class RankService {

    // 랭크 보정 상수 K
    @Value("${rank.constant.k}")
    private int rankConstantK;

    // 랭크 점수 계산 시 최소 변화량
    @Value("${rank.min.value}")
    private int rankMinValue;

    private final RankRepository rankRepository;

    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    public String getRankName(int rankScore){
        return "UNRANKED";
    }

    public CalculatedScore calculateScore(Team aTeam, Team bTeam, int aTeamScore, int bTeamScore){

        double aWinProbability = getWinProbability(aTeam.getRankScore(), bTeam.getRankScore());
        double bWinProbability = getWinProbability(bTeam.getRankScore(), aTeam.getRankScore());

        int calculatedAteamRankScore;
        int calculatedBTeamRankScore;
        // A팀이 승리한 경우
        if(aTeamScore > bTeamScore){
            calculatedAteamRankScore = calculateRankScore(aTeam.getRankScore(), aWinProbability, WinResult.WIN);
            calculatedBTeamRankScore = calculateRankScore(bTeam.getRankScore(), bWinProbability, WinResult.LOSE);
        }
        // B팀이 승리한 경우
        else if (aTeamScore < bTeamScore){
            calculatedAteamRankScore = calculateRankScore(aTeam.getRankScore(), aWinProbability, WinResult.LOSE);
            calculatedBTeamRankScore = calculateRankScore(bTeam.getRankScore(), bWinProbability, WinResult.WIN);
        }
        // 무승부한 경우
        else {
            calculatedAteamRankScore = calculateRankScore(aTeam.getRankScore(), aWinProbability, WinResult.DRAW);
            calculatedBTeamRankScore = calculateRankScore(bTeam.getRankScore(), bWinProbability, WinResult.DRAW);
        }
        return new CalculatedScore(calculatedAteamRankScore, calculatedBTeamRankScore);
    }

    /**
     *
     * 경기 결과에 따라 점수 계산
     * 경기후의 점수 = 경기전의 점수 + K * (경기결과 - 예측된 승률)
     *   --> 경기결과: 승리시 1, 무승부시 0.5, 패배시 0
     * 최소한 rankMinValue만큼 점수 변화가 되도록 보정됨.
     * @param beforeScore
     * @param winProbability
     * @param result
     * @return
     */
    private int calculateRankScore(int beforeScore, double winProbability, WinResult result){
        // 점수 계산
        Long afterScoreLong = Math.round(beforeScore + rankConstantK * (result.getValue() - winProbability));
        int afterScore = afterScoreLong.intValue();

        // 승리 또는 패배 시 최소한 rankMinValue 만큼은 점수가 변하도록 보정
        if(result == WinResult.WIN && (afterScore - beforeScore) < rankMinValue){
            afterScore = beforeScore + rankMinValue;
        }
        if(result == WinResult.LOSE && (beforeScore - afterScore) < rankMinValue) {
            afterScore = beforeScore - rankMinValue;
        }

        return afterScore;
    }

    /**
     * 우리팀이 이길 확률을 계산
     * 우리팀이 이길 확률 = 1 / (10^(상대팀점수 - 우리팀점수) / 400 + 1)
     * @param ourTeamRank
     * @param otherTeamRank
     * @return
     */
    private double getWinProbability(int ourTeamRank, int otherTeamRank){
        return 1.0 / (1 + Math.pow(10, otherTeamRank - ourTeamRank)/400);
    }

    /**
     * 점수가 높은 상위 랭크 팀 목록을 조회
     * @param numOfTeam 조회할 팀의 수
     * @return
     */
    public List<Ranking> selectRankingList(Integer numOfTeam){

        List<Ranking> rankingList = rankRepository.selectRankingList(numOfTeam);

        if(!rankingList.isEmpty()) {
            // 포함된 팀들의 ID를 조회
            Set<Integer> teamIdSet = new HashSet<>();
            rankingList.forEach(ranking -> teamIdSet.add(ranking.getId()));

            // 각 팀의 팀원 개수를 확인
            Map<Integer, Integer> memberCountMap = new HashMap<>();
            rankRepository.selectMemberCount(teamIdSet).forEach(memberCount -> memberCountMap.put(memberCount.getTeamId(), memberCount.getCount()));

            // 결과 생성
            List<Ranking> result = new ArrayList<>();
            rankingList.forEach(ranking -> {
                Integer teamId = ranking.getId();
                result.add(new Ranking(ranking.getRank(), teamId, ranking.getCiPath(), ranking.getName(), ranking.getRankScore(), memberCountMap.get(teamId)));
            });
            return result;

        } else {
            return rankingList;
        }
    }
}
