package capston.server.photo.dto;


import capston.server.photo.domain.Photo;
import capston.server.trip.domain.Trip;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AlbumResponseDto {
    private Long tripId;
    private String title;
    private List<String> photoUrls;

    public AlbumResponseDto(Trip trip, List<String> photoUrls) {
        this.tripId = trip.getId();
        this.title = trip.getTitle();
        this.photoUrls = photoUrls;
    }
}
