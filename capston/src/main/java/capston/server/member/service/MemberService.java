package capston.server.member.service;

import capston.server.member.domain.Member;
import capston.server.member.domain.ProviderType;
import capston.server.member.dto.MemberLoginResponseDto;
import capston.server.member.dto.MemberNicknameRequestDto;
import capston.server.oauth2.jwt.Token;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {
    Member save(Member member);
    Member findMember(String token);
    Token reIssue(String rememberedToken);
    MemberLoginResponseDto loginMember(String authToken, ProviderType provider);

    Member modifyProfileImg(Member member, MultipartFile file);
    Member modifyProfilleName(Member member, MemberNicknameRequestDto dto);

}
