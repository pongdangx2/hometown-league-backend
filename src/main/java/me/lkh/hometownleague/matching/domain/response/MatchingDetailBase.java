package me.lkh.hometownleague.matching.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchingDetailBase {
    private final Integer id;               // 매칭 요청 매핑 ID
    private final Integer teamId;           // 매칭 요청한 팀 ID

    @JsonIgnore
    private final Integer otherMatchRequestId;      // 상대팀 요청 ID

    private final String requestTimestamp;  // 매칭 요청 시간
    private final String makeTimestamp;     // 매칭 잡힌 시간
    private final String matchTimestamp;    // 경기 에정 시간
    private final String status;            // 매칭 상태 코드
    private final String statusName;        // 매칭 상태 코드명
    private final Double matchLatitude;     // 매치 장소 위도
    private final Double matchLongitude;    // 매치 장소 경도
    private final String roadAddress;       // 매치 장소 도로명주소
    private final String jibunAddress;      // 매치 장소 지번 주소

    public MatchingDetailBase(Integer id, Integer teamId, Integer otherMatchRequestId, String requestTimestamp, String makeTimestamp, String matchTimestamp, String status, String statusName, Double matchLatitude, Double matchLongitude, String roadAddress, String jibunAddress) {
        this.id = id;
        this.teamId = teamId;
        this.otherMatchRequestId = otherMatchRequestId;
        this.requestTimestamp = requestTimestamp;
        this.makeTimestamp = makeTimestamp;
        this.matchTimestamp = matchTimestamp;
        this.status = status;
        this.statusName = statusName;
        this.matchLatitude = matchLatitude;
        this.matchLongitude = matchLongitude;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
    }

    public Integer getOtherMatchRequestId() {
        return otherMatchRequestId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public String getStatusName() {
        return statusName;
    }

    public Integer getId() {
        return id;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public String getMakeTimestamp() {
        return makeTimestamp;
    }

    public String getMatchTimestamp() {
        return matchTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public Double getMatchLatitude() {
        return matchLatitude;
    }

    public Double getMatchLongitude() {
        return matchLongitude;
    }

    public String getRoadAddress() {
        return roadAddress;
    }

    public String getJibunAddress() {
        return jibunAddress;
    }
}
