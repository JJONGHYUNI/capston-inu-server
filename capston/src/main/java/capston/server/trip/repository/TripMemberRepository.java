package capston.server.trip.repository;

import capston.server.member.domain.Member;
import capston.server.trip.domain.Trip;
import capston.server.trip.domain.TripMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripMemberRepository extends JpaRepository<TripMember,Long> {
    Optional<TripMember> findByTripAndMember(Trip trip, Member member);
}
