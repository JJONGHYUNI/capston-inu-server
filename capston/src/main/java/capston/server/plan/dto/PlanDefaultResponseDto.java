package capston.server.plan.dto;

import capston.server.plan.domain.Plan;
import lombok.Data;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class PlanDefaultResponseDto {

    private String startTime;
    private String endTime;
    private String activity;

    public PlanDefaultResponseDto(Plan plan){
        this.startTime=timeFormmat(plan.getStartTime());
        this.endTime=timeFormmat(plan.getEndTime());
        this.activity=plan.getActivity();
    }
    private String timeFormmat(Time time){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(time);
    }
}
