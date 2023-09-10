package me.lkh.hometownleague.common.util;

import org.springframework.stereotype.Service;

/**
 * 랭크를 어떻게 정할지에 대한 로직을 가진 클래스.
 */
@Service
public class RankService {

    public String getRankName(int rankScore){
        return "UNRANKED";
    }
}
