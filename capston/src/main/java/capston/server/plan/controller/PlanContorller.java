package capston.server.plan.controller;

import capston.server.common.DefaultResponseDto;
import capston.server.plan.dto.PlanGetResponseDto;
import capston.server.plan.dto.PlanSaveRequestDto;
import capston.server.plan.service.PlanService;
import io.swagger.annotations.Api;
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
    @PostMapping("/{tripId}/save")
    public ResponseEntity<DefaultResponseDto> planSave(@PathVariable Long tripId,@RequestBody PlanSaveRequestDto dto, @RequestHeader("X-AUTH-TOKEN") String token){
        planService.newSave(tripId,dto,token);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @GetMapping("/{tripId}/plan")
    public ResponseEntity<List<PlanGetResponseDto>> findPlanByTripId(@PathVariable Long tripId, @RequestHeader("X-AUTH-TOKEN") String token){
        List<PlanGetResponseDto> dto = planService.findPlan(tripId,token);
        return ResponseEntity.ok(dto);
    }
}
