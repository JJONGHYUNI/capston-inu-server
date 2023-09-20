package capston.server.trip.dto;

import capston.server.photo.domain.Photo;
import capston.server.trip.domain.Trip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TripDefaultResponseDto {
    private Long tripId;
    private String title;
    private String location;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
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
