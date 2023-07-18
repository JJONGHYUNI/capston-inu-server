package capston.server.review.dto;

import capston.server.member.domain.Member;
import capston.server.review.domain.Review;
import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("리뷰 저장 요청 dto")
public class ReviewSaveRequestDto {
    private String content;
    public Review toEntity(Member member , Trip trip){
        return Review.builder()
                .content(content)
                .member(member)
                .trip(trip)
                .build();
    }
}
