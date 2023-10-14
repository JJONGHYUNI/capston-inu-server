package capston.server.plan.dto;

import capston.server.plan.domain.PlanDay;
import capston.server.trip.domain.Trip;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlanDayRequestDto {
    private LocalDateTime day;

    public PlanDay toEntity(LocalDateTime day, Trip trip){
        return PlanDay.builder()
                .day(day)
                .trip(trip)
                .build();
    }
}
