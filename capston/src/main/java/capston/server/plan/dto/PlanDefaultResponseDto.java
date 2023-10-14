package capston.server.plan.dto;

import capston.server.common.DateUtils;
import capston.server.plan.domain.Plan;
import lombok.Data;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class PlanDefaultResponseDto {

    private LocalDateTime startTime;
    private String activity;

    public PlanDefaultResponseDto(Plan plan){
        this.startTime = plan.getStartTime();
        this.activity=plan.getActivity();
    }
}
