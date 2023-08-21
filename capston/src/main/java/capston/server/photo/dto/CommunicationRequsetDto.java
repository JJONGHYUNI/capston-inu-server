package capston.server.photo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "플라스크 서버로 보낼 데이터")
public class CommunicationRequsetDto {
    @Schema(example = "https://capston.s3.ap-northeast-2.amazonaws.com/서울여행/1/2027cd25-0f4e-402a-a552-e26ff2344b71.jpeg")
    private List<String> photoList;
    @Schema(example = "1")
    private int num;
    @Schema(example = "1")
    private int photoNum;
}
