package me.lkh.hometownleague.matching.service;

import me.lkh.hometownleague.common.code.MatchingStatusCode;
import me.lkh.hometownleague.common.exception.matching.MatchingAlreadyExistException;
import me.lkh.hometownleague.common.exception.matching.MatchingRequestAlreadyExistException;
import me.lkh.hometownleague.common.exception.matching.MatchingRequestFailException;
import me.lkh.hometownleague.common.exception.matching.NoSuchMatchingRequestIdException;
import me.lkh.hometownleague.matching.domain.MatchingListElement;
import me.lkh.hometownleague.matching.domain.MatchingQueueElement;
import me.lkh.hometownleague.matching.domain.response.MatchingDetailBase;
import me.lkh.hometownleague.matching.domain.response.MatchingDetailResponse;
import me.lkh.hometownleague.matching.domain.response.MatchingDetailTeam;
import me.lkh.hometownleague.matching.repository.MatchingRepository;
import me.lkh.hometownleague.team.domain.Team;
import me.lkh.hometownleague.team.service.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;

    private final TeamService teamService;

    private final MatchingRedisService matchingRedisService;

    public MatchingService(MatchingRepository matchingRepository, TeamService teamService, MatchingRedisService matchingRedisService) {
        this.matchingRepository = matchingRepository;
        this.teamService = teamService;
        this.matchingRedisService = matchingRedisService;
    }

    @Transactional
    public void makeMatchingRequest(String userId, Integer teamId){
        teamService.isOwner(userId, teamId);

        // DB에 요청 정보 저장
        int matchingId = makeMatchingRequestInDb(teamId);

        // Redis 대기열에 저장
        makeMatchingRequestInRedis(matchingId, teamId);
    }

    private void makeMatchingRequestInRedis(int matchingId, Integer teamId){
        // Redis 대기열에 저장
        matchingRedisService.makeMatchingRequest(MatchingQueueElement.makeMatchingQueueElementOfNow(matchingId, teamId));
    }

    private int makeMatchingRequestInDb(Integer teamId){
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

        return matchingRepository.selectMatchingRequest(teamId);
    }

    public List<MatchingListElement> selectMatching(String userId){
        return matchingRepository.selectMatching(userId);
    }

    public MatchingDetailResponse selectMatchingDetail(Integer matchingRequestId){
        // 1. 매칭 기본정보 조회
        Optional<MatchingDetailBase> optionalMatchingDetailBase = Optional.ofNullable(matchingRepository.selectMatchingDetailBase(matchingRequestId));
        optionalMatchingDetailBase.orElseThrow(NoSuchMatchingRequestIdException::new);

        MatchingDetailBase matchingDetailBase = optionalMatchingDetailBase.get();

        MatchingDetailTeam ourTeam = selectMatchingDetailTeam(matchingRequestId);
        MatchingDetailTeam otherTeam = null;

        // 대기중이 아닌 경우 에만 매칭 정보가 존재,  대기중인 경우는 아직 매칭정보 미존재하고 매칭 요청정보만 조회
        if(!MatchingStatusCode.WAIT.getStatusCode().equals(matchingDetailBase.getStatus())){
            otherTeam = selectMatchingDetailTeam(matchingDetailBase.getOtherMatchRequestId());
        }

        return new MatchingDetailResponse(matchingDetailBase, ourTeam, otherTeam);
    }

    private MatchingDetailTeam selectMatchingDetailTeam(Integer matchingRequestId){
        MatchingDetailTeam matchingDetailTeam = matchingRepository.selectMatchingDetailTeam(matchingRequestId);

        return new MatchingDetailTeam(matchingDetailTeam.getTeamId()
                                    , matchingDetailTeam.getStatus()
                                    , matchingDetailTeam.getStatusName()
                                    , matchingDetailTeam.getAcceptTimestamp()
                                    , teamService.isExist(matchingDetailTeam.getTeamId())
                                    , teamService.selectUserOfTeam(matchingDetailTeam.getTeamId())
        );
    }
}
