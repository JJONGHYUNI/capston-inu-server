package capston.server.review.service;

import capston.server.member.domain.Member;
import capston.server.member.service.MemberServiceImpl;
import capston.server.review.domain.Review;
import capston.server.review.dto.ReviewSaveRequestDto;
import capston.server.review.repository.ReviewRepository;
import capston.server.trip.domain.Trip;
import capston.server.trip.service.TripServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final TripServiceImpl tripService;

    @Override
    public Review save(Long tripId, Member member, ReviewSaveRequestDto dto){
        Trip trip = tripService.findTripById(tripId);
        return reviewRepository.save(dto.toEntity(member,trip));
    }
}
