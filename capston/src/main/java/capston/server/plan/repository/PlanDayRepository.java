package capston.server.plan.repository;

import capston.server.plan.domain.PlanDay;
import capston.server.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanDayRepository extends JpaRepository<PlanDay, Long> {
    List<PlanDay> findAllByTripOrderByDayAsc(Trip trip);
}
