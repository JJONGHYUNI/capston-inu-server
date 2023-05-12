package capston.server.oauth2.jwt;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class Token {
    private String authToken;
    private String rememberedToken;

    public Token(String token, String rememberedToken){
        this.authToken=token;
        this.rememberedToken=rememberedToken;
    }
}
