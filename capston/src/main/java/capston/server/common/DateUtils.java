package capston.server.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String formatMonthDay(LocalDate time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        return time.format(formatter);
    }
}
