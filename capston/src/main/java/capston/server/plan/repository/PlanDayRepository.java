package capston.server.plan.repository;

import capston.server.plan.domain.PlanDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanDayRepository extends JpaRepository<PlanDay, Long> {
}
