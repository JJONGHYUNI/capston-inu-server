package capston.server.member.controller;

import capston.server.exception.CustomException;
import capston.server.exception.ExceptionResponseDto;
import capston.server.member.domain.ProviderType;
import capston.server.member.dto.MemberLoginResponseDto;
import capston.server.member.dto.RememberedLoginRequestDto;
import capston.server.member.service.MemberService;
import capston.server.member.service.MemberServiceImpl;
import capston.server.oauth2.AccessToken;
import capston.server.oauth2.jwt.Token;
import capston.server.oauth2.jwt.dto.TokenResponeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static capston.server.exception.Code.*;

@Api(tags = "로그인")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final MemberService memberService;

    @PostMapping("/oauth2/{provider}")
    public ResponseEntity<MemberLoginResponseDto> loginSocial(@RequestBody AccessToken accessToken, @PathVariable ProviderType provider) {
        MemberLoginResponseDto responseDto = memberService.loginMember(accessToken.getToken(), provider);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "자동 로그인 요청", description = "리프레쉬 토큰으로 자동 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "성공 응답",
                    response = TokenResponeDto.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "토큰이 없거나 잘못됐습니다.",
                    response = ExceptionResponseDto.class
            ),
            @ApiResponse(
                    code = 403,
                    message = "접근 권한이 없습니다",
                    response = ExceptionResponseDto.class
            ),
            @ApiResponse(
                    code = 404,
                    message = "일치하는 회원이 없습니다.",
                    response = ExceptionResponseDto.class
            ),
            @ApiResponse(
                    code = 500,
                    message = "서버 에러입니다.",
                    response = ExceptionResponseDto.class
            )
    })
    @PostMapping("/remembered")
    public ResponseEntity<TokenResponeDto> rememberedLogin(@RequestBody RememberedLoginRequestDto dto){
        if (!dto.getRememberedToken().isEmpty()){
            Token token = memberService.reIssue(dto.getRememberedToken());
            TokenResponeDto responeDto = new TokenResponeDto(token);
            return ResponseEntity.ok(responeDto);
        }
        throw new CustomException(null, TOKEN_NOT_FOUND);
    }
}
