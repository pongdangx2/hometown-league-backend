package me.lkh.hometownleague.matching.service;

import me.lkh.hometownleague.common.code.MatchingStatusCode;
import me.lkh.hometownleague.common.exception.common.CommonErrorException;
import me.lkh.hometownleague.common.exception.matching.*;
import me.lkh.hometownleague.common.exception.team.NoSuchTeamIdException;
import me.lkh.hometownleague.common.exception.team.NotOwnerException;
import me.lkh.hometownleague.matching.domain.MatchingInfo;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return matchingRepository.selectMatching(userId).stream().filter(matchingListElement -> {
            // 아직 처리되지 않은 경우 포함
            if("N".equals(matchingListElement.getProcessYn()))
                return true;
            else {
                // 완료된 경우 걸러내고, 완료되지 않은 경우 포함
                return !"E".equals(matchingListElement.getStatus());
            }
        }).collect(Collectors.toList());
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

    public void deleteMatchingRequest(Integer matchingRequestId, String userId) throws NotOwnerException, NoSuchTeamIdException, NoSuchMatchingRequestIdException {
        Optional.ofNullable(matchingRepository.matchingRequestDeleteCheck(matchingRequestId))
                .ifPresentOrElse(matchingRequestDeleteCheck -> {
                    // 소유주가 아니면 삭제할 수 없음.
                    Team selectedTeam = teamService.isOwner(userId, matchingRequestDeleteCheck.getTeamId());

                    // 이미 처리된 경우 삭제할 수 없음.
                    if(matchingRequestDeleteCheck.getStatus() != null || "Y".equals(matchingRequestDeleteCheck.getProcessYn())){
                        throw new MatchingRequestAlreadyProcessedException();
                    }
                    // 매칭요청 삭제에 실패한 경우
                    if(1 != matchingRepository.deleteMatchingRequest(matchingRequestId)){
                        throw new CannotCancelMatchingRequestException();
                    }
                },
                () -> { throw new NoSuchMatchingRequestIdException(); });
    }

    @Transactional
    public void acceptMatching(Integer matchingRequestId, String userId){
        Optional.ofNullable(matchingRepository.matchingRequestDeleteCheck(matchingRequestId))
                .ifPresentOrElse(matchingRequestDeleteCheck -> {
                            // 소유주가 아니면 수락할 수 없음.
                            Team selectedTeam = teamService.isOwner(userId, matchingRequestDeleteCheck.getTeamId());

                            // 한팀 수락 혹은 수락대기가 아닌 경우 수락 불가.
                            if(!("C".equals(matchingRequestDeleteCheck.getStatus()) || "O".equals(matchingRequestDeleteCheck.getStatus()))){
                                throw new CannotAcceptMatchingException();
                            }

                            Optional.ofNullable(matchingRepository.selectMatchingInfo(matchingRequestId)).ifPresentOrElse(matchingInfo -> {
                                        // 팀의 요청 상태가 수락대기인 경우 수락 가능
                                        if("C".equals(matchingInfo.getStatus())){
                                            // 팀의 요청상태가 수락대기이며, 매칭의 상태가 한팀수락 혹은 수락대기인 경우 -> 수락 가능
                                            // 팀의 요청상태를 수락으로 변경(S)
                                            matchingRepository.updateMatchingInfoToAccept(matchingInfo.getId());

                                            Map<String, Object> param = new HashMap<>();
                                            param.put("id", matchingRequestDeleteCheck.getRequestMappingId());
                                            // 매칭요청상태가 수락대기(C)인 경우 -> 한팀수락(O)로 변경
                                            if("C".equals(matchingRequestDeleteCheck.getStatus())){
                                                param.put("status", "O");
                                            }
                                            // 매칭요청상태가 O(한팀수락)인 경우 -> 양팀수락(S)로 변경
                                            else {
                                                param.put("status", "S");
                                            }
                                            matchingRepository.updateMatfingRequestMapping(param);

                                        }
                                        // 팀의 요청 상태가 수락대기가 아닌 경우 수락 불가
                                        else {
                                            throw new OnlyConfirmWaitingCanBeAcceptedException();
                                        }
                                    },
                                    () -> { throw new NoSuchMatchingRequestIdException();   }
                            );
                        },
                        () -> { throw new NoSuchMatchingRequestIdException(); });
    }

    @Transactional
    public void refuseMatching(Integer matchingRequestId, String userId) {

        Optional.ofNullable(matchingRepository.matchingRequestDeleteCheck(matchingRequestId))
                .ifPresentOrElse(matchingRequestDeleteCheck -> {
                            // 소유주가 아니면 거절할 수 없음.
                            Team selectedTeam = teamService.isOwner(userId, matchingRequestDeleteCheck.getTeamId());

                            // 우리팀이 수락대기중일 때만 거절 가능
                            MatchingInfo ourTeamMatchingInfo = matchingRepository.selectMatchingInfo(matchingRequestId);
                            if("C".equals(ourTeamMatchingInfo.getStatus())){
                                Integer otherTeamRequestId = matchingRepository.selectOtherTeamRequestId(matchingRequestId);    // 상대팀 요청ID

                                // 상대팀의 ID를 구해서 데이터를 삭제하고 큐에 최우선 순위 삽입
                                Optional.ofNullable(matchingRepository.matchingRequestDeleteCheck(otherTeamRequestId))
                                        .ifPresentOrElse(otherTeamMatchingRequestDeleteCheck -> {

                                            // 우리팀 매칭 요청 정보 삭제
                                            if(matchingRepository.deleteMatchingRequest(matchingRequestId) <= 0){
                                                throw new CommonErrorException();
                                            }

                                            // 상대팀 매칭 요청 정보 삭제
                                            if(matchingRepository.deleteMatchingRequest(otherTeamRequestId) <= 0){
                                                throw new CommonErrorException();
                                            }

                                            // 매칭 요청 매핑정보 삭제
                                            if(matchingRepository.deleteMatchingRequestMapping(matchingRequestId) <= 0){
                                                throw new CommonErrorException();
                                            }

                                            // 우리팀 매칭정보 삭제
                                            if(matchingRepository.deleteMatchingInfo(matchingRequestId) <= 0){
                                                throw new CommonErrorException();
                                            }

                                            // 상대팀 매칭정보 삭제
                                            if(matchingRepository.deleteMatchingInfo(otherTeamRequestId) <= 0){
                                                throw new CommonErrorException();
                                            }

                                            // DB에 요청 정보 저장
                                            int matchingId = makeMatchingRequestInDb(otherTeamMatchingRequestDeleteCheck.getTeamId());
                                            // Redis 대기열 맨 앞에 저장
                                            matchingRedisService.pushLeft(MatchingQueueElement.makeMatchingQueueElementOfNow(matchingId, otherTeamMatchingRequestDeleteCheck.getTeamId()));
                                        },
                                        () -> {
                                            throw new CommonErrorException();
                                        });
                            } else {
                                throw new MatchingIsNotRefusableException();
                            }
                        },
                        () -> {
                            throw new NoSuchMatchingRequestIdException();
                        });
    }

}
