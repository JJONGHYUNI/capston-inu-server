package capston.server.trip.dto;

import capston.server.member.dto.MemberGetResponseDto;
import capston.server.member.dto.MemberNameResponseDto;
import capston.server.review.dto.ReviewDefaultResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class TripDetailResponseDto {
    private TripDefaultResponseDto trip;
    private List<MemberGetResponseDto> participants;
    private List<ReviewDefaultResponseDto> review;

    public TripDetailResponseDto(TripDefaultResponseDto trip, List<MemberGetResponseDto> participants, List<ReviewDefaultResponseDto> review){
        this.trip = trip;
        this.participants = participants;
        this.review = review;
    }
}
