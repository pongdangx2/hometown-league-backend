package me.lkh.hometownleague.common.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HometownLeagueUtil {

    public static String integerToNullableString(Integer value){
        return value == null ? null : String.valueOf(value);
    }

    public static String getCurrentTimestamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static LocalTime getLocalTimeFromString(String time){
        return LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(2, 4)));
    }
}
