package capston.server.review.service;

import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.review.domain.Review;
import capston.server.review.dto.ReviewSaveRequestDto;
import capston.server.review.repository.ReviewRepository;
import capston.server.trip.domain.Trip;
import capston.server.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final TripService tripService;

    public Review save(Long tripId,String token, ReviewSaveRequestDto dto){
        Member member = memberService.findMember(token);
        Trip trip = tripService.findTripById(tripId);
        return reviewRepository.save(dto.toEntity(member,trip));
    }
}
