package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "여행 일정 처음 요청 dto")
public class TripNewSaveRequestDto {
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalDate;
    @Schema(example = "123.4567891234")
    private BigDecimal latitude;
    @Schema(example = "123.4567891234")
    private BigDecimal longitude;

    public Trip toEntity(){
        return Trip.builder()
                .location(location)
                .departureDate(departureDate)
                .arrivalDate(arrivalDate)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
