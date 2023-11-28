package me.lkh.hometownleague.rank.controller;

import me.lkh.hometownleague.common.exception.ErrorCode;
import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.response.CommonResponse;
import me.lkh.hometownleague.rank.domain.Ranking;
import me.lkh.hometownleague.rank.service.RankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {

    private final RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping("/{numOfTeam}")
    public CommonResponse selectRankingList(@PathVariable Integer numOfTeam) throws CommonErrorException {
        List<Ranking> rankingList = rankService.selectRankingList(numOfTeam);
        return new CommonResponse<>(rankingList);
    }
}
