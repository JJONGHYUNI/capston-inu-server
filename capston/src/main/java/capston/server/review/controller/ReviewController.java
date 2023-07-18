package capston.server.review.controller;

import capston.server.common.DefaultResponseDto;
import capston.server.review.dto.ReviewSaveRequestDto;
import capston.server.review.service.ReviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "리뷰")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/{tripId}/save")
    public ResponseEntity<DefaultResponseDto> reviewSave(@PathVariable Long tripId,@RequestBody ReviewSaveRequestDto dto, @RequestHeader("X-AUTH-TOKEN") String token){
        reviewService.save(tripId,token,dto);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }
}
