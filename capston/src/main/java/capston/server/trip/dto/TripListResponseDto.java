package capston.server.trip.dto;

import capston.server.common.DateUtils;
import capston.server.trip.domain.Trip;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TripListResponseDto {
    private Long tripId;
    private String title;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;

    public TripListResponseDto(Trip trip){
        this.title=trip.getTitle();
        this.arrivalDate= trip.getArrivalDate();
        this.departureDate= trip.getDepartureDate();
        this.tripId=trip.getId();
    }
}
