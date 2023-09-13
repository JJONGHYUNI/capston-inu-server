package capston.server.plan.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlanGetResponseDto {
    private int day;
    private List<PlanDefaultResponseDto> plans;

    public PlanGetResponseDto(int day, List<PlanDefaultResponseDto> plans){
        this.day = day;
        this.plans = plans;
    }
}
