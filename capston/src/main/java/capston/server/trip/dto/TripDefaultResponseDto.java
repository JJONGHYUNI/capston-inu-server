package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TripDefaultResponseDto {
    private Long tripId;
    private String title;
    private String location;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String mainPhoto;

    public TripDefaultResponseDto(Trip trip){
        this.tripId=trip.getId();
        this.title=trip.getTitle();
        this.location=trip.getLocation();
        this.departureDate=trip.getDepartureDate();
        this.arrivalDate=trip.getArrivalDate();
        this.latitude=trip.getLatitude();
        this.longitude=trip.getLongitude();
        this.mainPhoto=trip.getMainPhoto();
    }
}
