package me.lkh.hometownleague.schedule.matching.service;

import me.lkh.hometownleague.matching.domain.MatchingQueueElement;
import me.lkh.hometownleague.matching.service.MatchingRedisService;
import me.lkh.hometownleague.schedule.matching.domain.MatchingRequestInfo;
import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingBaseInfo;
import me.lkh.hometownleague.schedule.matching.repository.MatchMakingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MatchMakingService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MatchingRedisService matchingRedisService;

    private final MatchMakingRepository matchMakingRepository;

    @Value("${matching.max-number}")
    private int maxNumber;

    public MatchMakingService(MatchingRedisService matchingRedisService, MatchMakingRepository matchMakingRepository) {
        this.matchingRedisService = matchingRedisService;
        this.matchMakingRepository = matchMakingRepository;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void matchMakingJob(){
        // redis 대기열에서 가져오기
//        MatchingQueueElement matchingQueueElement = matchingRedisService.popLeft();
        MatchingQueueElement matchingQueueElement = matchingRedisService.getLeft();
        Optional<MatchingRequestInfo> optionalMatchingRequestInfo = Optional.ofNullable(matchMakingRepository.selectMatchingRequestInfo(matchingQueueElement.getMatchingRequestId()));
        optionalMatchingRequestInfo.ifPresent(matchingRequestInfo -> {
            // 이미 처리되지 않은 경우에만 매칭 처리
            if(!"Y".equals(matchingRequestInfo.getProcessYn())){
                try{
                    // 1. 처리 되었다고 업데이트하여 다른 배치에서 중복작업을 방지
                    matchMakingRepository.updateProcessYn(matchingRequestInfo.getId());

                    // 최대 횟수까지만 시도
                    int times = 1;
                    while (times <= maxNumber){

                        // 2. 매칭이 성공하면 종료
                        if(matchingStep(matchingRequestInfo, times)){
                            // 매칭 시간은 현재로 업데이트
                            matchMakingRepository.updateProcessTimestamp(matchingRequestInfo.getId());
                            return ;
                        }

                    }

                    // 3. 최대횟수 안에 매칭이 실패하면 최우선순위로 다시 넣고 종료
                    throw new RuntimeException();

                } catch (RuntimeException runtimeException){
                    // 1. 처리중 오류 발생 시 대기열에 최우선순위로 다시 삽입 (Left)
                    matchingRedisService.pushLeft(matchingQueueElement);

                    // 2. RuntimeException을 발생시켜 transaction rollback
                    throw new RuntimeException();
                }
            }
        });
    }

    private boolean matchingStep(MatchingRequestInfo matchingRequestInfo, int times){

        int scoreDiff = times * 10;
        // 1. 1차 필터링(점수 기준) - 내 점수와의 차이가 scoreDiff보다 작은 팀 목록 조회
        List<TeamMatchingBaseInfo> teamMatchingBaseInfoList = matchMakingRepository.selectTeamMatchingBaseInfo(matchingRequestInfo.getId(), matchingRequestInfo.getRankScore(), scoreDiff);

        return false;
    }
}
