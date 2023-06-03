package capston.server.trip.controller;

import capston.server.common.DefaultResponseDto;
import capston.server.trip.domain.Trip;
import capston.server.trip.dto.TripCodeResponseDto;
import capston.server.trip.dto.TripDefaultResponseDto;
import capston.server.trip.dto.TripNewSaveRequestDto;
import capston.server.trip.dto.TripSaveRequestDto;
import capston.server.trip.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "여행")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;

    @Operation(summary = "여행 종료시 사진과 함께저장",description = "여행 종료시 사진과 함께 업로드 요청")
    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseDto<Object>> saveTrip(@ModelAttribute TripSaveRequestDto dto, @RequestHeader("X-AUTH-TOKEN") String token){
        tripService.saveTrip(dto,token);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @Operation(summary = "공유 코드 발급 요청" , description = "공유할 여행의 코드 발급")
    @PostMapping("/{tripId}/code")
    public ResponseEntity<TripCodeResponseDto> issueCode(@PathVariable Long tripId){
        TripCodeResponseDto dto = new TripCodeResponseDto();
        dto.setCode(tripService.issueCode(tripId));
        return ResponseEntity.ok(dto);
    }
    @Operation(summary = "공유 코드로 여행 참가",description = "공유 코드로 여행에 참가")
    @PostMapping("/{code}")
    public ResponseEntity<TripDefaultResponseDto> joinTrip(@PathVariable int code,@RequestHeader("X-AUTH-TOKEN") String token){
        Trip trip =tripService.joinTrip(code,token);
        return ResponseEntity.ok(new TripDefaultResponseDto(trip));

    }

    @Operation(summary = "여행 일정 짤때 저장",description = "여행 일정 단계에서 여행 새로 저장")
    @PostMapping("/new")
    public ResponseEntity<DefaultResponseDto> newTrip(@RequestBody TripNewSaveRequestDto dto, @RequestHeader("X-AUTH-TOKEN") String token){
        tripService.newSaveTrip(dto,token);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @Operation(summary = "여행 단건 조회",description = "여행 하나 조회")
    @GetMapping("/{tripId}")
    public ResponseEntity<TripDefaultResponseDto> findOneTrip(@PathVariable Long tripId,@RequestHeader("X-AUTH-TOKEN") String token){
        Trip trip = tripService.findTripById(tripId);
        return ResponseEntity.ok(new TripDefaultResponseDto(trip));
    }
}
