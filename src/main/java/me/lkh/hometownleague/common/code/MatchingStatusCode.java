package me.lkh.hometownleague.common.code;

public enum MatchingStatusCode {
    WAIT("W"),      // 매칭 대기
    NO_TEAM_ACCEPT("N"),    // 양팀 모두 미수락
    ONE_TEAM_ACCEPT("O"),   // 한팀 수락
    SUCCESS("S"),   // 매치 성사
    END("E")        // 매치 종료
    ;

    private String statusCode;

    MatchingStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode(){
        return this.statusCode;
    }
}
