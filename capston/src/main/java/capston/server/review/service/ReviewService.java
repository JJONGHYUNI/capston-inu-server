package capston.server.review.service;

import capston.server.member.domain.Member;
import capston.server.review.domain.Review;
import capston.server.review.dto.ReviewSaveRequestDto;

public interface ReviewService {
        Review save(Long tripId, Member member, ReviewSaveRequestDto dto);
}
