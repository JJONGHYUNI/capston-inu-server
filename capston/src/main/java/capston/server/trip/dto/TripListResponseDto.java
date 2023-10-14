package capston.server.trip.dto;

import capston.server.common.DateUtils;
import capston.server.trip.domain.Trip;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class TripListResponseDto {
    private Long tripId;
    private String title;
    private String location;
    private String arrivalDate;
    private String departureDate;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public TripListResponseDto(Trip trip){
        this.title=trip.getTitle();
        this.arrivalDate= DateUtils.formatTimeToSecond(trip.getArrivalDate());
        this.departureDate= DateUtils.formatTimeToSecond(trip.getDepartureDate());
        this.tripId=trip.getId();
        this.latitude = trip.getLatitude();
        this.longitude = trip.getLongitude();
        this.location = trip.getLocation();
    }
}
