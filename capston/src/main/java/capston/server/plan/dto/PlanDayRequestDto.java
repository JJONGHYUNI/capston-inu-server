package capston.server.plan.dto;

import capston.server.plan.domain.PlanDay;
import capston.server.trip.domain.Trip;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlanDayRequestDto {
    private LocalDateTime day;

    private List<PlanDefaultResponseDto> plans ;

    public PlanDay toEntity(LocalDateTime day, Trip trip){
        return PlanDay.builder()
                .day(day)
                .trip(trip)
                .build();
    }
}
