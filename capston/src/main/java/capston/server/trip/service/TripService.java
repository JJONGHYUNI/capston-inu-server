package capston.server.trip.service;

import capston.server.member.domain.Member;
import capston.server.trip.domain.Trip;
import capston.server.trip.domain.TripMember;
import capston.server.trip.dto.TripDefaultResponseDto;
import capston.server.trip.dto.TripNewSaveRequestDto;
import capston.server.trip.dto.TripSaveRequestDto;

import java.util.List;

public interface TripService {
    Trip save(Trip trip);
    Trip findTripById(Long id);
    Trip saveTrip(TripSaveRequestDto dto, String token);
    Trip newSaveTrip(TripNewSaveRequestDto dto, String token);
    TripMember saveTripMember(Trip trip , Member member);
    int issueCode(Long id);
    Trip joinTrip(int code,String token);
    List<TripDefaultResponseDto> findAllTrip(String token);
    boolean memberByJoinTrip(Member member , Trip trip);

}
