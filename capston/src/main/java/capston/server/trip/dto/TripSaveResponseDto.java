package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import lombok.Data;

@Data
public class TripSaveResponseDto {
    private final String recommendPhoto;

    public TripSaveResponseDto(Trip trip) {
        this.recommendPhoto = trip.getMainPhoto();
    }
}
