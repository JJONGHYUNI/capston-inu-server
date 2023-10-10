package capston.server.member.dto;

import capston.server.member.domain.Member;
import lombok.Data;

@Data
public class MemberNameResponseDto {
    private String name;
    public MemberNameResponseDto(Member member){
        this.name = member.getName();
    }
}
