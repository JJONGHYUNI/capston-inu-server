package capston.server.plan.controller;

import capston.server.common.DefaultResponseDto;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.plan.dto.PlanAllSaveRequestDto;
import capston.server.plan.dto.PlanGetResponseDto;
import capston.server.plan.dto.PlanSaveRequestDto;
import capston.server.plan.service.PlanService;
import capston.server.trip.domain.Trip;
import capston.server.trip.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "계획")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class PlanContorller {
    private final PlanService planService;
    private final MemberService memberService;
    private final TripService tripService;
    @Operation(summary = "여행의 일정 수정", description = "여행 일정 수정")
    @PutMapping("/{tripId}/modify")
    public ResponseEntity<DefaultResponseDto> planSave(@PathVariable Long tripId,@RequestBody List<PlanAllSaveRequestDto> dto, @RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        Trip trip = tripService.findTripById(tripId);
        planService.modifyPlan(trip,dto,member);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @Operation(summary = "여행 일정 정보 한 번에 보내기",description = "여행 일정 정보 한 번에 보내기")
    @PostMapping("/{tripId}/save")
    public ResponseEntity<DefaultResponseDto> allPlanSave(@PathVariable Long tripId, @RequestBody List<PlanAllSaveRequestDto> dto, @RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        Trip trip = tripService.findTripById(tripId);
        planService.modifyPlan(trip,dto,member);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @Operation(summary = "해당 여행의 일정 조회",description = "여행 일정을 조회")
    @GetMapping("/{tripId}/plan")
    public ResponseEntity<List<PlanGetResponseDto>> findPlanByTripId(@PathVariable Long tripId, @RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        Trip trip = tripService.findTripById(tripId);
        List<PlanGetResponseDto> dto = planService.findPlan(trip,member);
        return ResponseEntity.ok(dto);
    }
}
