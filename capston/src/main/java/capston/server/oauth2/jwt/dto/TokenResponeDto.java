package capston.server.oauth2.jwt.dto;

import capston.server.oauth2.jwt.Token;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResponeDto {
    private String authToken;
    private String rememberedToken;

    public TokenResponeDto(Token token){
        this.authToken=token.getAuthToken();
        this.rememberedToken=token.getRememberedToken();
    }
}
