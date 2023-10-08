package capston.server.trip.dto;

import capston.server.common.DateUtils;
import capston.server.trip.domain.Trip;
import lombok.Data;


@Data
public class TripListResponseDto {
    private Long tripId;
    private String title;
    private String arrivalDate;
    private String departureDate;

    public TripListResponseDto(Trip trip){
        this.title=trip.getTitle();
        this.arrivalDate= DateUtils.formatMonthDay(trip.getArrivalDate());
        this.departureDate=DateUtils.formatMonthDay(trip.getDepartureDate());
        this.tripId=trip.getId();
    }
}
