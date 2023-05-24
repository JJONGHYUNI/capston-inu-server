package capston.server.trip.controller;

import capston.server.common.DefaultResponseDto;
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
    public ResponseEntity<DefaultResponseDto<Object>> saveTrip(@ModelAttribute TripSaveRequestDto dto){
        tripService.saveTrip(dto);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }
}
