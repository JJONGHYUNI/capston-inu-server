package capston.server.common;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
}
