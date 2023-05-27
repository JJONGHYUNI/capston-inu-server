package capston.server.trip.repository;

import capston.server.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip,Long> {
    Optional<Trip> findByCode(int code);
}
