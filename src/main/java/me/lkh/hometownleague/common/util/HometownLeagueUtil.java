package me.lkh.hometownleague.common.util;

public class HometownLeagueUtil {

    public static String integerToNullableString(Integer value){
        return value == null ? null : String.valueOf(value);
    }
}
