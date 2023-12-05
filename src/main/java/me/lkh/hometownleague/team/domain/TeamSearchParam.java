package me.lkh.hometownleague.team.domain;

public class TeamSearchParam {
    private final String addressSi;
    private final String addressGungu;
    private final Integer dayOfWeek;
    private final String time;
    private final Integer fromScore;
    private final Integer toScore;
    private final String name;
    private final Integer offset;
    private final Integer count;

    public TeamSearchParam(String addressSi, String addressGungu, Integer dayOfWeek, String time, Integer fromScore, Integer toScore, String name, Integer offset, Integer count) {
        this.addressSi = addressSi;
        this.addressGungu = addressGungu;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.fromScore = fromScore;
        this.toScore = toScore;
        this.name = name;
        this.offset = offset;
        this.count = count;
    }

    public String getAddressSi() {
        return addressSi;
    }

    public String getAddressGungu() {
        return addressGungu;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public Integer getFromScore() {
        return fromScore;
    }

    public Integer getToScore() {
        return toScore;
    }

    public String getName() {
        return name;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getCount() {
        return count;
    }
}
