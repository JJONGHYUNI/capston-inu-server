package capston.server.trip.dto;

import capston.server.member.dto.MemberNameResponseDto;
import capston.server.review.dto.ReviewDefaultResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class TripDetailResponseDto {
    private TripDefaultResponseDto trip;
    private List<MemberNameResponseDto> participants;
    private List<ReviewDefaultResponseDto> review;

    public TripDetailResponseDto(TripDefaultResponseDto trip, List<MemberNameResponseDto> participants, List<ReviewDefaultResponseDto> review){
        this.trip = trip;
        this.participants = participants;
        this.review = review;
    }
}
