package capston.server.member.service;


import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.domain.ProviderType;
import capston.server.member.domain.Role;
import capston.server.member.dto.MemberLoginResponseDto;
import capston.server.member.dto.MemberNicknameRequestDto;
import capston.server.member.repository.MemberRepository;
import capston.server.oauth2.jwt.JwtResultType;
import capston.server.oauth2.jwt.Token;
import capston.server.oauth2.jwt.service.JwtService;
import capston.server.oauth2.service.ProviderService;
import capston.server.oauth2.userinfo.OAuth2UserDto;
import capston.server.photo.domain.MemberPhoto;
import capston.server.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static capston.server.exception.Code.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    @Value("${spring.default.profile-img}")
    private String defaultProfile;

    private final MemberRepository memberRepository;

    private final JwtService jwtService;

    private final ProviderService providerService;

    private final PhotoService photoService;

    @Transactional
    @Override
    public Member save(Member member){
        try{
            return memberRepository.save(member);
        }catch (RuntimeException e){
            throw new CustomException(null, SERVER_ERROR);
        }
    }
    @Transactional
    @Override
    public Member findMember(String token){
        String email = jwtService.getEmail(token);
        return memberRepository.findByEmail(email).orElseThrow(()->new CustomException(null,MEMBER_NOT_FOUND));
    }

    /**
     * 자동 로그인
     */
    @Transactional
    @Override
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
    @Override
    public MemberLoginResponseDto loginMember(String authToken, ProviderType provider){
        OAuth2UserDto profile = providerService.getProfile(authToken,provider);
        Optional<Member> findMember = memberRepository.findByEmailAndProviderType(profile.getEmail(),provider);
        Token token = new Token();
        if (findMember.isPresent()){
            token = accept(findMember.get());
            return new MemberLoginResponseDto(token,true);
        }else{
            token = firstLogin(profile,provider);
            return new MemberLoginResponseDto(token,false);
        }
    }

    @Transactional
    @Override
    public Member modifyProfileImg(Member member, MultipartFile file) {
        photoService.savePhoto(member, file);
        return member;
    }
    @Transactional
    @Override
    public Member modifyProfilleName(Member member, MemberNicknameRequestDto dto) {
        member.modifyNickname(dto.getNickname());
        return member;
    }

    private Token accept(Member member) {
        Token token = jwtService.sendAccessAndRefreshToken(member);
        return token;
    }

    private Token firstLogin(OAuth2UserDto profile,ProviderType provider){
        Member member = Member.builder()
                .email(profile.getEmail())
                .providerType(provider)
                .name(profile.getName())
                .role(Role.GUEST)
                .build();
        member = save(member);
        photoService.save(MemberPhoto.builder()
                .member(member)
                .photoUrl(defaultProfile)
                .build());
        Token token = jwtService.sendAccessAndRefreshToken(member);
        return token;
    }
}
