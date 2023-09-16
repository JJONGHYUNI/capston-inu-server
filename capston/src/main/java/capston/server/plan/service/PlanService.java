package capston.server.plan.service;

import capston.server.plan.domain.Plan;
import capston.server.plan.dto.PlanGetResponseDto;
import capston.server.plan.dto.PlanSaveRequestDto;

import java.util.List;
import java.util.Map;

public interface PlanService {
    Plan save(Plan plan);
    Plan newSave(Long tripId, PlanSaveRequestDto dto, String token);
    List<PlanGetResponseDto> findPlan(Long tripId, String token);
    Map<Integer,List<Plan>> findPlanByDay(List<Plan> plans);

}
