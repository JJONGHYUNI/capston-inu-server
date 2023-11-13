package capston.server.photo.dto;

import capston.server.trip.domain.Trip;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PhotoByTripResponseDto {
    private String title;
    private List<String> photoUrls;

    public PhotoByTripResponseDto(Trip trip) {
        this.title = trip.getTitle();
        this.photoUrls = trip.getPhotos().stream().map(photo -> photo.getPhotoUrl()).collect(Collectors.toList());
    }
}
