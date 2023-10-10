package capston.server.review.dto;

import capston.server.review.domain.Review;
import lombok.Data;

@Data
public class ReviewDefaultResponseDto {
    private String name;
    private String content;
    private String profileImg;

    public ReviewDefaultResponseDto(Review review){
        this.name = review.getMember().getName();
        this.content = review.getContent();
        this.profileImg = review.getMember().getMemberPhoto().getPhotoUrl();
    }
}
