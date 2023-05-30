package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "Trip 일정 단게 저장 요청 dto")
public class TripNewSaveRequestDto {
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalDate;

    public Trip toEntity(){
        return Trip.builder()
                .location(location)
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .build();
    }
}
