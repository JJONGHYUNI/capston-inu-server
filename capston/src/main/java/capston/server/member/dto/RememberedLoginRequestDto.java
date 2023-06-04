package capston.server.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "자동 로그인 토큰 요청")
public class RememberedLoginRequestDto {
    private String rememberedToken;
}
