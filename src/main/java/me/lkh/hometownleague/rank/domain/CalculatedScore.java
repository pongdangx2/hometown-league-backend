package me.lkh.hometownleague.rank.domain;

public class CalculatedScore {
    private final int aTeamScore;
    private final int bTeamScore;

    public CalculatedScore(int aTeamScore, int bTeamScore) {
        this.aTeamScore = aTeamScore;
        this.bTeamScore = bTeamScore;
    }

    public int getaTeamScore() {
        return aTeamScore;
    }

    public int getbTeamScore() {
        return bTeamScore;
    }
}
