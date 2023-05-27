package capston.server.trip.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "공유 코드로 여행 불러오기 dto")
public class TripCodeRequestDto {
    private int code;
}
