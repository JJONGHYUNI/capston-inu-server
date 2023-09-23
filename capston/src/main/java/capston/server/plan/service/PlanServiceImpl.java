package capston.server.plan.service;

import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.plan.domain.Plan;
import capston.server.plan.dto.PlanDefaultResponseDto;
import capston.server.plan.dto.PlanGetResponseDto;
import capston.server.plan.dto.PlanSaveRequestDto;
import capston.server.plan.repository.PlanRepository;
import capston.server.trip.domain.Trip;
import capston.server.trip.service.TripServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static capston.server.exception.Code.SERVER_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final MemberService memberService;

    private final TripServiceImpl tripService;

    @Override
    public Plan save(Plan plan){
        try{
            return planRepository.save(plan);
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }
    @Override
    public Plan newSave(Long tripId,PlanSaveRequestDto dto, Member member){
        Trip trip = tripService.findTripById(tripId);
        return save(dto.toEntity(trip));
    }

    @Override
    public List<PlanGetResponseDto> findPlan(Long tripId,Member member){
        Trip trip = tripService.findTripById(tripId);
        List<PlanGetResponseDto> result = new ArrayList<>();
        List<Plan> plans = planRepository.findAllByTripOrderByDayAsc(trip);
        Map<Integer,List<Plan>> planList = findPlanByDay(plans);
        for(Map.Entry<Integer,List<Plan>> entry: planList.entrySet() ){
            List<Plan> plan = entry.getValue();
            List<PlanDefaultResponseDto> planDefaultResponseDtos = plan.stream().map(plan1 -> new PlanDefaultResponseDto(plan1)).collect(Collectors.toList());
            PlanGetResponseDto planGetResponseDto = new PlanGetResponseDto(entry.getKey(), planDefaultResponseDtos);
            result.add(planGetResponseDto);
        }
        return result;
    }

    @Override
    public Map<Integer,List<Plan>> findPlanByDay(List<Plan> plans){
        Map<Integer,List<Plan>> dividedList = new LinkedHashMap<>();
        int idx = plans.get(0).getDay();
        log.info("{}",plans.get(0).getDay());
        log.info("{}",plans.get(1).getDay());
        List<Plan> newList = new ArrayList<>();
        for(Plan plan : plans){
            if (idx==plan.getDay()){
                newList.add(plan);
            }else{
                dividedList.put(idx,newList);
                idx=plan.getDay();
                newList = new ArrayList<>();
            }
        }
        dividedList.put(idx,newList);
        return dividedList;
    }
}
