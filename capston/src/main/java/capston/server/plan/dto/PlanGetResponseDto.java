package capston.server.plan.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlanGetResponseDto {
    private LocalDateTime day;
    private List<PlanDefaultResponseDto> plans;

    public PlanGetResponseDto(LocalDateTime day, List<PlanDefaultResponseDto> plans){
        this.day = day;
        this.plans = plans;
    }
}
