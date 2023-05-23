package capston.server.oauth2.jwt.service;


import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.domain.Role;
import capston.server.member.repository.MemberRepository;
import capston.server.oauth2.jwt.JwtResultType;
import capston.server.oauth2.jwt.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static capston.server.exception.Code.MEMBER_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    /**
     * subject와 claim으로 email
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";

    private final MemberRepository memberRepository;

    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    //accessToken 생성
    public String createAccessToken(String email, Role roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles",roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpirationPeriod))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    //refreshToken 생성
    public String createRefreshToken(String email, Role roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles",roles);
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        return refreshToken;
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }


    //access+refresh 받기
    public Token sendAccessAndRefreshToken(Member member) {
        String accessToken= createAccessToken(member.getEmail(),member.getRole());
        String refreshToken= createRefreshToken(member.getEmail(),member.getRole());
        updateRefreshToken(member.getEmail(),refreshToken);
        return new Token(accessToken,refreshToken);
    }


    /**
     * 헤더에서 AccessToken 추출
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader));
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    public void updateRefreshToken(String email, String refreshToken) {
        memberRepository.findByEmail(email)
                .ifPresentOrElse(member -> {
                    member.updateRefreshToken(refreshToken);
                    memberRepository.saveAndFlush(member);},
                    () -> new CustomException(null, MEMBER_NOT_FOUND)
                );
    }

    public JwtResultType isTokenValid(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return JwtResultType.VALID_JWT;
        }catch (ExpiredJwtException e){
            log.info("만료된 jwt 토큰");
            return JwtResultType.EXPIRED_JWT;
        }catch (Exception e){
            log.info("잘못된 jwt 토큰");
            return JwtResultType.INVALID_JWT;
        }
    }

}
