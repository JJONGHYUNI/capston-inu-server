package capston.server.member.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        return memberRepository.findByEmail(username)
                .orElseThrow(()-> new CustomException(null, Code.MEMBER_NOT_FOUND));
    }
}
