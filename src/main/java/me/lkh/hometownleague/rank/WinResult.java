package me.lkh.hometownleague.rank;

public enum WinResult {
      WIN(1.0)
    , DRAW(0.5)
    , LOSE(0.0);

    private final double value;

    WinResult(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
