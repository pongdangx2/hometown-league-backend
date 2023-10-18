package me.lkh.hometownleague.common.util;

import me.lkh.hometownleague.schedule.matching.domain.TeamMatchingTime;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

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

    public static String getMatchTimestamp(TeamMatchingTime teamMatchingTime){
        LocalDateTime minDate = LocalDateTime.from(LocalDateTime.now().plusDays(6));

        LocalDateTime nextDate;
        switch(teamMatchingTime.getDayOfWeek()){
            case 1:
                nextDate = minDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                break;
            case 2:
                nextDate = minDate.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                break;
            case 3:
                nextDate = minDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                break;
            case 4:
                nextDate = minDate.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                break;
            case 5:
                nextDate = minDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                break;
            case 6:
                nextDate = minDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                break;
            default:
                nextDate = minDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        }

        LocalDateTime matchingDateTime = nextDate.withHour(Integer.parseInt(teamMatchingTime.getFromTime().substring(0, 2)))
                .withMinute(Integer.parseInt(teamMatchingTime.getFromTime().substring(2, 4)));

        return matchingDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }
}
