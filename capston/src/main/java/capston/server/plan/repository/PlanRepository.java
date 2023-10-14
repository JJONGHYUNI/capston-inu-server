package capston.server.plan.repository;

import capston.server.plan.domain.Plan;
import capston.server.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan,Long> {
}
