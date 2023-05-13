package capston.server.member.service;


import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.domain.ProviderType;
import capston.server.member.domain.Role;
import capston.server.member.dto.MemberLoginResponseDto;
import capston.server.member.repository.MemberRepository;
import capston.server.oauth2.jwt.JwtResultType;
import capston.server.oauth2.jwt.Token;
import capston.server.oauth2.jwt.service.JwtService;
import capston.server.oauth2.service.ProviderService;
import capston.server.oauth2.userinfo.OAuth2UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static capston.server.exception.Code.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final JwtService jwtService;

    private final ProviderService providerService;

    @Transactional
    public Member save(Member member){
        try{
            return memberRepository.save(member);
        }catch (RuntimeException e){
            throw new CustomException(null, SERVER_ERROR);
        }
    }

    /**
     * 자동 로그인
     */
    @Transactional
    public Token reIssue(String rememberedToken){
        if(!(jwtService.isTokenValid(rememberedToken)== JwtResultType.VALID_JWT)){
            throw new CustomException(null,WRONG_TYPE_TOKEN);
        }
        if(memberRepository.findByRefreshToken(rememberedToken).isPresent()){
            Member member = memberRepository.findByRefreshToken(rememberedToken).get();
            String authToken=jwtService.createAccessToken(member.getEmail(),member.getRole());
            String refreshToken= jwtService.createRefreshToken(member.getEmail(),member.getRole());
            jwtService.updateRefreshToken(member.getEmail(),refreshToken);
            return new Token(authToken,refreshToken);
        }else{throw new CustomException(null,MEMBER_NOT_FOUND);}
    }
    @Transactional
    public MemberLoginResponseDto loginMember(String authToken, ProviderType provider){
        OAuth2UserDto profile = providerService.getProfile(authToken,provider);
        Optional<Member> findMember = memberRepository.findByEmailAndProviderType(profile.getEmail(),provider);
        findMember.ifPresent(this::accept);
        Member member = Member.builder()
                .email(profile.getEmail())
                .providerType(provider)
                .role(Role.GUEST)
                .build();
        member = save(member);
        Token token = jwtService.sendAccessAndRefreshToken(member);
        return new MemberLoginResponseDto(token,false);
    }

    private MemberLoginResponseDto accept(Member member) {
        Token token = jwtService.sendAccessAndRefreshToken(member);
        MemberLoginResponseDto memberLoginResponseDto = new MemberLoginResponseDto(token, true);
        return memberLoginResponseDto;
    }
}
