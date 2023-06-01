package capston.server.trip.dto;

import capston.server.trip.domain.Trip;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@ApiModel(value = "Trip 사진과 함께 저장 요청 dto")
public class TripSaveRequestDto {
    private Long tripId;
    private String title;
    private String location;
    private int mainPhoto;
    private List<MultipartFile> files;
}
