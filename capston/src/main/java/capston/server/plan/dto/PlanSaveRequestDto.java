package capston.server.plan.dto;

import capston.server.plan.domain.Plan;
import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Data
@ApiModel(value = "계획 저장 요청 dto")
public class PlanSaveRequestDto {
    private int day;
    @Schema(example = "12:34")
    private String startTime;

    private String activity;

    public Plan toEntity(Trip trip){
        return Plan.builder()
                .day(day)
                .startTime(timeFormat(startTime))
                .activity(activity)
                .trip(trip)
                .build();
    }
    public Time timeFormat(String time){
        String[] parts=time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        String timeString = String.format("%02d:%02d:00",hour,minute);
        return Time.valueOf(timeString);
    }
}
