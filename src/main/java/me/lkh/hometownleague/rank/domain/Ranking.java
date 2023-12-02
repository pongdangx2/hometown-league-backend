package me.lkh.hometownleague.rank.domain;

public class Ranking {
    private final Integer rank;
    private final Integer id;
    private final String ciPath;
    private final String name;
    private final Integer rankScore;
    private final Integer playerCount;

    public Ranking(Integer rank, Integer id, String ciPath, String name, Integer rankScore) {
        this(rank, id, ciPath, name, rankScore, null);
    }

    public Ranking(Integer rank, Integer id, String ciPath, String name, Integer rankScore, Integer playerCount) {
        this.rank = rank;
        this.id = id;
        this.ciPath = ciPath;
        this.name = name;
        this.rankScore = rankScore;
        this.playerCount = playerCount;
    }

    public Integer getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public String getCiPath() {
        return ciPath;
    }

    public String getName() {
        return name;
    }

    public int getRankScore() {
        return rankScore;
    }

    public Integer getPlayerCount() {
        return playerCount;
    }
}
