package capston.server.member.repository;

import capston.server.member.domain.Member;
import capston.server.member.domain.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndProviderType(String email, ProviderType providerType);
    Optional<Member> findByEmail(String email);
}
