package capston.server.trip.controller;

import capston.server.common.DefaultResponseDto;
import capston.server.trip.domain.Trip;
import capston.server.trip.dto.TripCodeResponseDto;
import capston.server.trip.dto.TripDefaultResponseDto;
import capston.server.trip.dto.TripSaveRequestDto;
import capston.server.trip.service.TripService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "여행")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;

    @ApiOperation(value = "여행 저장")
    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseDto<Object>> saveTrip(@ModelAttribute TripSaveRequestDto dto,@RequestHeader("X-AUTH_TOKEN") String token){
        tripService.saveTrip(dto,token);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @ApiOperation(value = "공유 코드 발급")
    @PostMapping("/{tripId}/code")
    public ResponseEntity<TripCodeResponseDto> issueCode(@PathVariable Long tripId){
        TripCodeResponseDto dto = new TripCodeResponseDto();
        dto.setCode(tripService.issueCode(tripId));
        return ResponseEntity.ok(dto);
    }
    @ApiOperation(value = "공유 코드로 여행 참가")
    @PostMapping("/code")
    public ResponseEntity<TripDefaultResponseDto> joinTrip(@RequestParam int code,@RequestHeader("X-AUTH_TOKEN") String token){
        Trip trip =tripService.joinTrip(code,token);
        return ResponseEntity.ok(new TripDefaultResponseDto(trip));

    }
}
