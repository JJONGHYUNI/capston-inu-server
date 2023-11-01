package capston.server.member.dto;

import capston.server.member.domain.Member;
import lombok.Data;

@Data
public class MemberGetResponseDto {
    private String nickname;
    private String profileImgUrl;

    public MemberGetResponseDto(Member member) {
        this.nickname = member.getName();
        this.profileImgUrl = member.getMemberPhoto().getPhotoUrl();
    }
}
