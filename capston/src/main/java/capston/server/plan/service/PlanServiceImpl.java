package capston.server.plan.service;

import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.plan.domain.Plan;
import capston.server.plan.domain.PlanDay;
import capston.server.plan.dto.PlanAllSaveRequestDto;
import capston.server.plan.dto.PlanDefaultResponseDto;
import capston.server.plan.dto.PlanGetResponseDto;
import capston.server.plan.dto.PlanSaveRequestDto;
import capston.server.plan.repository.PlanDayRepository;
import capston.server.plan.repository.PlanRepository;
import capston.server.trip.domain.Trip;
import capston.server.trip.service.TripServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static capston.server.exception.Code.SERVER_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final PlanDayRepository planDayRepository;

    @Override
    public Plan save(Plan plan){
        try{
            return planRepository.save(plan);
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    @Override
    public PlanDay save(PlanDay planDay) {
        try{
            return planDayRepository.save(planDay);
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    @Override
    public Plan newSave(Trip trip,PlanSaveRequestDto dto, Member member){
//        return save(dto.toEntity(trip, 1));
        return null;
    }

    @Override
    public String planAllSave(Trip trip, List<PlanAllSaveRequestDto> dtos, Member member) {
        dtos.forEach(dto -> {
            PlanDay planDay = save(PlanDay.builder().day(dto.getDay()).trip(trip).build());
            dto.getPlans().forEach(
                    planSaveRequestDto -> {
                        save(planSaveRequestDto.toEntity(planDay));
                    }
            );
        });
        return "성공";
    }

    @Override
    public List<PlanGetResponseDto> findPlan(Trip trip,Member member){
//        List<PlanGetResponseDto> result = new ArrayList<>();
//        List<Plan> plans = planRepository.findAllByTripOrderByDayAsc(trip);
//        Map<LocalDateTime,List<Plan>> planList = findPlanByDay(plans);
//        log.info("{}", planList.toString());
//        for(Map.Entry<LocalDateTime,List<Plan>> entry: planList.entrySet() ){
//            List<PlanDefaultResponseDto> planDefaultResponseDtos = entry.getValue().stream().map(plan -> new PlanDefaultResponseDto(plan)).collect(Collectors.toList());
//            PlanGetResponseDto planGetResponseDto = new PlanGetResponseDto(entry.getKey(), planDefaultResponseDtos);
//            result.add(planGetResponseDto);
//        }
//        return result;
        return null;
    }

    @Override
    public Map<LocalDateTime,List<Plan>> findPlanByDay(List<Plan> plans){
//        Map<LocalDateTime,List<Plan>> dividedList = new LinkedHashMap<>();
//        LocalDateTime idx = plans.get(0).getDay();
//        List<Plan> newList = new ArrayList<>();
//        for(Plan plan : plans){
//            if (idx.isEqual(plan.getDay())){
//
//                newList.add(plan);
//            }else{
//                dividedList.put(idx,newList);
//                idx=plan.getDay();
//                newList = new ArrayList<>();
//                newList.add(plan);
//            }
//        }
//        dividedList.put(idx,newList);
//        return dividedList;
        return null;
    }
}
