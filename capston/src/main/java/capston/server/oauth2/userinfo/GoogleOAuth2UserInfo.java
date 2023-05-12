package capston.server.oauth2.userinfo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleOAuth2UserInfo {
    private String email;
    private String name;
}
