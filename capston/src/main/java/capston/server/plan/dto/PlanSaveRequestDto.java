package capston.server.plan.dto;

import capston.server.common.DateUtils;
import capston.server.plan.domain.Plan;
import capston.server.plan.domain.PlanDay;
import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@ApiModel(value = "계획 dto")
public class PlanSaveRequestDto {
    @Schema(description = "시작 시간")
    private LocalDateTime startTime;

    @Schema(description = "활동" , example = "인천 공항 출발")
    private String activity;
    public Plan toEntity(PlanDay planDay) {
        return Plan.builder()
                .activity(activity)
                .startTime(startTime)
                .planDay(planDay)
                .build();
    }
}
