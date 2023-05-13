package capston.server.member.controller;

import capston.server.member.domain.ProviderType;
import capston.server.member.dto.MemberLoginResponseDto;
import capston.server.member.service.MemberService;
import capston.server.oauth2.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/login/oauth2/{provider}")
    public ResponseEntity<MemberLoginResponseDto> loginSocial(@RequestBody AccessToken accessToken, @PathVariable ProviderType provider) {
        MemberLoginResponseDto responseDto = memberService.loginMember(accessToken.getToken(), provider);
        return ResponseEntity.ok(responseDto);
    }
}
