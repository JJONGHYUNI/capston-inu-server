package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TripDefaultResponseDto {
    private Long tripId;
    private String title;
    private String location;

    public TripDefaultResponseDto(Trip trip){
        this.tripId=trip.getId();
        this.title=trip.getTitle();
        this.location=trip.getLocation();
    }
}
