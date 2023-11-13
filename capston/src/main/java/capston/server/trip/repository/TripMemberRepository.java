package capston.server.trip.repository;

import capston.server.member.domain.Member;
import capston.server.trip.domain.Trip;
import capston.server.trip.domain.TripMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TripMemberRepository extends JpaRepository<TripMember,Long> {
    Optional<TripMember> findByTripAndMember(Trip trip, Member member);
    List<TripMember> findByMemberId(Long memberId);
    List<TripMember> findAllByTripId(Long tripId);

    // MemberId를 기반으로 조회한 TripMember 중에서 Trip의 completed가 1인 것들 조회
    @Query(
            "SELECT tm FROM TripMember tm JOIN Trip t ON tm.trip = t WHERE t.completed = true AND tm.member = :member"
    )
    Page<TripMember> findTripMembersWithCompletedTrips(@Param("member") Member member, Pageable pageable);
}
