package capston.server.plan.dto;

import capston.server.common.DateUtils;
import capston.server.plan.domain.PlanDay;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PlanGetResponseDto {
    private String day;
    private List<PlanDefaultResponseDto> plans;

    public PlanGetResponseDto(PlanDay planDay){
        this.day = DateUtils.formatTimeToSecond(planDay.getDay());
        this.plans = planDay.getPlans().stream().map(plan -> new PlanDefaultResponseDto(plan)).collect(Collectors.toList());
    }
}
