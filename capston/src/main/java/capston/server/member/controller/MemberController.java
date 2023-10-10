package capston.server.member.controller;

import capston.server.common.DefaultResponseDto;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "멤버")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "프로필 이미지 변경", description = "토큰과 프로필 이미지를 업로드하여 프로필 이미지를 업데이트 합니다.")
    @PostMapping("/profile")
    public ResponseEntity<DefaultResponseDto<Object>> modifyProfileImg(@RequestHeader("X-AUTH-TOKEN") String token, @RequestParam(value = "image") MultipartFile file){
        Member member = memberService.findMember(token);
        memberService.modifyProfileImg(member, file);
        return ResponseEntity.ok(DefaultResponseDto.builder().build());
    }
}