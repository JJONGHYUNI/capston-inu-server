package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "Trip 저장 요청 dto")
public class TripSaveRequestDto {
    private String title;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalDate;
    private List<MultipartFile> files = new ArrayList<>();
    private int mainPhoto;

    public Trip toEntity(){
        return Trip.builder()
                .title(title)
                .location(location)
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .build();
    }
}
