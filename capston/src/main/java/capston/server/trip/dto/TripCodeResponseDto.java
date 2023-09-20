package capston.server.trip.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "공유 코드 발급 dto")
public class TripCodeResponseDto {
    private int code;

    public TripCodeResponseDto(int code){
        this.code=code;
    }
}
