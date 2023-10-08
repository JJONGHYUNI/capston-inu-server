package capston.server.trip.controller;

import capston.server.common.DefaultResponseDto;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.trip.domain.Trip;
import capston.server.trip.dto.*;
import capston.server.trip.service.TripService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "여행")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;
    private final MemberService memberService;

    @Operation(summary = "여행 종료시 사진과 함께저장",description = "여행 종료시 사진과 함께 업로드 요청")
    @PostMapping(value = "/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultResponseDto<Object>> saveTrip(@ModelAttribute TripSaveRequestDto dto, @RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        tripService.saveTrip(dto,member);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @Operation(summary = "공유 코드 발급 요청" , description = "공유할 여행의 코드 발급")
    @PostMapping("/{tripId}/code")
    public ResponseEntity<TripCodeResponseDto> issueCode(@PathVariable Long tripId, @RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        TripCodeResponseDto dto = new TripCodeResponseDto(tripService.issueCode(tripId,member));
        return ResponseEntity.ok(dto);
    }
    @Operation(summary = "공유 코드로 여행 참가",description = "공유 코드로 여행에 참가")
    @PostMapping("/{code}")
    public ResponseEntity<TripDefaultResponseDto> joinTrip(@PathVariable int code,@RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        Trip trip =tripService.joinTrip(code,member);
        return ResponseEntity.ok(new TripDefaultResponseDto(trip));
    }

    @Operation(summary = "여행 일정 짤때 저장",description = "여행 일정 단계에서 여행 새로 저장")
    @PostMapping("/new")
    public ResponseEntity<DefaultResponseDto> newTrip(@RequestBody TripNewSaveRequestDto dto, @RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        tripService.newSaveTrip(dto,member);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }

    @Operation(summary = "여행 단건 조회",description = "여행 하나 조회")
    @GetMapping("/{tripId}")
    public ResponseEntity<TripDefaultResponseDto> findOneTrip(@PathVariable Long tripId,@RequestHeader("X-AUTH-TOKEN") String token){
        Trip trip = tripService.findTripById(tripId);
        return ResponseEntity.ok(new TripDefaultResponseDto(trip));
    }

    @Operation(summary = "여행 전체 조회",description = "내가 참여한 여행 모두 불러오기")
    @GetMapping("/all")
    public ResponseEntity<List<TripDefaultResponseDto>> findAllTrip(@RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        List<TripDefaultResponseDto> result = tripService.findAllTrip(member).stream().map(trip -> new TripDefaultResponseDto(trip)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "여행 목록 조회",description = "여행 목록 조회하기")
    @GetMapping("/list")
    public ResponseEntity<List<TripListResponseDto>> findTripList(@RequestHeader("X-AUTH-TOKEN") String token){
        Member member = memberService.findMember(token);
        List<TripListResponseDto> result = tripService.findAllTripByCompleted(member).stream().map(trip -> new TripListResponseDto(trip)).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
