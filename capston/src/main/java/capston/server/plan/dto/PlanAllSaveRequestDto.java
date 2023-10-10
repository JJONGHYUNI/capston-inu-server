package capston.server.plan.dto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(value = "계획 저장 요청 dto")
public class PlanAllSaveRequestDto {
    private int day;
    private List<PlanSaveRequestDto> plans;

}
