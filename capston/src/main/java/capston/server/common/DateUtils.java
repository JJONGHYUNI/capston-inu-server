package capston.server.common;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String formatMonthDay(LocalDate time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        return time.format(formatter);
    }

    public static String formatHourMinute(Time time){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(time);
    }

    public static Time formatStringToTime(String time) {
        String[] parts=time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        String timeString = String.format("%02d:%02d:00",hour,minute);
        return Time.valueOf(timeString);
    }
    public static String formatTimeToSecond(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return time.format(formatter) + "Z";
    }
}
