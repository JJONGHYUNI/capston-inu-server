package capston.server.plan.domain;


import capston.server.trip.domain.Trip;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "PLAN")
@AllArgsConstructor
public class Plan {
    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime startTime;

    private String activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="plan_day_id")
    private PlanDay planDay;

}
