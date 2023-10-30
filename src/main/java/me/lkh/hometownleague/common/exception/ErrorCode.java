package me.lkh.hometownleague.common.exception;

public enum ErrorCode {
     SUCCESS("0000", "성공")
    // 1000 : USER Error
    ,DUPLICATE_ID("1000", "이미 존재하는 ID입니다.")
    ,DUPLICATE_NAME("1001", "이미 존재하는 닉네임입니다.")
    ,NO_SUCH_USER_ID("1002", "존재하지 않는 ID입니다.")
    ,WRONG_PASSWORD("1003", "일치하지 않는 패스워드입니다.")
    ,UNAUTHORIZED("1004", "권한이 없습니다.")
    ,INVALID_SESSION("1005", "세션 정보가 없습니다.")
    ,DUPLICATE_LOGIN("1006", "이미 로그인 중입니다.")
    // 2000 : Team Error
    ,DUPLICATE_TEAM_NAME("2000", "이미 존재하는 팀명입니다.")
    ,CANNOT_INSERT_PLAY_TIME("2001", "시간 입력에 실패했습니다.")
    ,CANNOT_INSERT_PLAY_LOCATION("2002", "장소 입력에 실패했습니다.")
    ,NO_SUCH_TEAM_ID("2003", "존재하지 않는 팀ID입니다.")
    ,NOT_OWNER("2004", "소유주가 아닙니다.")
    ,CANNOT_UPDATE_PLAY_TIME("2005", "시간 수정에 실패했습니다.")
    ,CANNOT_UPDATE_PLAY_LOCATION("2006", "장소 수정에 실패했습니다.")
    ,NO_SUCH_PLAYER("2007", "팀의 소속 선수가 아닙니다.")
    ,CANNOT_REQUEST_JOIN_TEAM("2008", "가입요청에 실패했습니다.")
    ,DUPLICATE_TEAM_JOIN_REQUEST("2009", "이미 가입 요청되었습니다.")
    ,ALREADY_JOINED_TEAM("2010", "이미 가입된 팀입니다.")
    ,CANNOT_PRODUCE_TEAM_USER_MAPPING("2011", "팀 가입에 실패했습니다.")
    // 3000 : Matching Error
    ,MATCHING_REQUEST_ALREADY_EXIST("3000", "이미 요청했습니다.")
    ,MATCHING_ALREADY_EXIST("3001", "이전 경기가 종료되지 않았습니다.")
    ,MATCHING_REQUEST_FAIL("3002", "매칭 요청에 실패했습니다.")
    ,NO_SUCH_MATCHING_REQUEST_ID("3003", "존재하지 않는 매칭 요청 ID입니다.")
    ,MATCHING_REQUEST_ALREADY_PROCESSED("3004", "매칭 요청이 이미 처리되었습니다.")
    ,CANNOT_CANCEL_MATCHING_REQUEST("3005", "매칭요청 취소에 실패했습니다")
    ,CANNOT_ACCEPT_MATCHING("3006", "매칭을 수락할 수 없는 상태입니다.")
    ,ONLY_CONFIRM_WAITING_CAN_BE_ACCEPTED("3007", "수락대기상태인 경우만 수락할 수 있습니다.")
    // 9000 : Common Error
    ,COMMON_ERROR("9000", "처리 중 에러가 발생했습니다.")
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }

}
