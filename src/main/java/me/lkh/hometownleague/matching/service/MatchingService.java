package me.lkh.hometownleague.matching.service;

import me.lkh.hometownleague.common.exception.matching.MatchingAlreadyExistException;
import me.lkh.hometownleague.common.exception.matching.MatchingRequestAlreadyExistException;
import me.lkh.hometownleague.common.exception.matching.MatchingRequestFailException;
import me.lkh.hometownleague.matching.domain.MatchingListElement;
import me.lkh.hometownleague.matching.repository.MatchingRepository;
import me.lkh.hometownleague.team.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;

    private final TeamService teamService;

    public MatchingService(MatchingRepository matchingRepository, TeamService teamService) {
        this.matchingRepository = matchingRepository;
        this.teamService = teamService;
    }

    public void makeMatchingRequest(String userId, Integer teamId){
        teamService.isOwner(userId, teamId);
        // 이미 요청한 내용이 존재하는 경우
        Optional.ofNullable(matchingRepository.selectMatchingRequest(teamId)).ifPresent(matchingId -> {
            throw new MatchingRequestAlreadyExistException();
        });

        // 이미 진행중인 매칭이 있는 경우
        Optional.ofNullable(matchingRepository.selectMatchingInProgress(teamId)).ifPresent(matchingId -> {
            throw new MatchingAlreadyExistException();
        });

        // Insert 실패한 경우
        if(0 == matchingRepository.insertMatchingRequest(teamId)){
            throw new MatchingRequestFailException();
        }
    }
    public List<MatchingListElement> selectMatching(String userId){
        return matchingRepository.selectMatching(userId);
    }
}
