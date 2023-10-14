package capston.server.plan.service;

import capston.server.member.domain.Member;
import capston.server.plan.domain.Plan;
import capston.server.plan.domain.PlanDay;
import capston.server.plan.dto.PlanAllSaveRequestDto;
import capston.server.plan.dto.PlanGetResponseDto;
import capston.server.plan.dto.PlanSaveRequestDto;
import capston.server.trip.domain.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PlanService {
    Plan save(Plan plan);
    PlanDay save(PlanDay planDay);
    Plan newSave(Trip trip, PlanSaveRequestDto dto, Member member);
    String planAllSave(Trip trip, List<PlanAllSaveRequestDto> dto, Member member);
    List<PlanGetResponseDto> findPlan(Trip trip, Member member);
    Map<LocalDateTime,List<Plan>> findPlanByDay(List<Plan> plans);

}
