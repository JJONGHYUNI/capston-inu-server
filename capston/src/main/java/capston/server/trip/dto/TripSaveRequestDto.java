package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "Trip 저장 요청 dto")
public class TripSaveRequestDto {
    private String title;
    private String location;
    private List<MultipartFile> files = new ArrayList<>();

    public Trip toEntity(){
        return Trip.builder()
                .title(title)
                .location(location)
                .build();
    }
}
