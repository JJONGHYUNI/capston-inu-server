package capston.server.plan.service;

import capston.server.member.domain.Member;
import capston.server.plan.domain.Plan;
import capston.server.plan.dto.PlanGetResponseDto;
import capston.server.plan.dto.PlanSaveRequestDto;

import java.util.List;
import java.util.Map;

public interface PlanService {
    Plan save(Plan plan);
    Plan newSave(Long tripId, PlanSaveRequestDto dto, Member member);
    List<PlanGetResponseDto> findPlan(Long tripId, Member member);
    Map<Integer,List<Plan>> findPlanByDay(List<Plan> plans);

}
