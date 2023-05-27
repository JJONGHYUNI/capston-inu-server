package capston.server.trip.repository;

import capston.server.trip.domain.TripMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripMemberRepository extends JpaRepository<TripMember,Long> {
}
