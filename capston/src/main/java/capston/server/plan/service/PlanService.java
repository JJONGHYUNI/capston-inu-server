package capston.server.plan.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.member.service.MemberService;
import capston.server.plan.domain.Plan;
import capston.server.plan.dto.PlanSaveRequestDto;
import capston.server.plan.repository.PlanRepository;
import capston.server.trip.domain.Trip;
import capston.server.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capston.server.exception.Code.SERVER_ERROR;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanService {
    private final PlanRepository planRepository;
    private final MemberService memberService;

    private final TripService tripService;

    public Plan save(Plan plan){
        try{
            return planRepository.save(plan);
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    public Plan newSave(Long tripId,PlanSaveRequestDto dto, String token){
        memberService.findMember(token);
        Trip trip = tripService.findTripById(tripId);
        return save(dto.toEntity(trip));
    }
}
