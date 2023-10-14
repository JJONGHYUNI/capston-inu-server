package capston.server.photo.dto;

import capston.server.trip.domain.Trip;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PhotoDefaultResponseDto {
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private String title;
    private List<String> photoUrl;

}
