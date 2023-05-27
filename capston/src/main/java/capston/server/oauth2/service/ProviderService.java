package capston.server.oauth2.service;


import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.member.domain.ProviderType;
import capston.server.oauth2.userinfo.GoogleOAuth2UserInfo;
import capston.server.oauth2.userinfo.OAuth2UserDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {
    private static final String DEFAULT_OAUTH2_LOGIN_REQUEST_URL_PREFIX = "/login/oauth2/";
    private final RestTemplate restTemplate;

    @Value("${spring.social.google.url.profile}")
    private String googleUrl;

    private final Gson gson;

    public OAuth2UserDto getProfile(String authToken, ProviderType provider){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.set("Authorization", "Bearer " + authToken);
        String url = urlMapping(provider);
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(null,httpHeaders);

        try{
            ResponseEntity<String> response = restTemplate.postForEntity(url,request,String.class);
            if(response.getStatusCode()== HttpStatus.OK){
                return extractProfile(response,provider);
            }
        }catch(Exception e){
            throw new CustomException(e, Code.WRONG_TYPE_TOKEN);
        }
        throw new RuntimeException();
    }

    private String urlMapping(ProviderType checkProvider){
        if(checkProvider.equals(ProviderType.GOOGLE)) {
            return googleUrl;
        }
        return googleUrl;
    }

    private  OAuth2UserDto extractProfile(ResponseEntity<String> response, ProviderType providerType){
        if(providerType.equals(ProviderType.GOOGLE)){
            GoogleOAuth2UserInfo googleOAuth2UserInfo = gson.fromJson(response.getBody(), GoogleOAuth2UserInfo.class);
            return new OAuth2UserDto(googleOAuth2UserInfo.getEmail(),googleOAuth2UserInfo.getName());
        }
        throw new RuntimeException();
    }
}
