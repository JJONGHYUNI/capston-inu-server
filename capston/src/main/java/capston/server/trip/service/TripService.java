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
    Trip saveTrip(TripSaveRequestDto dto, Member member);
    Trip newSaveTrip(TripNewSaveRequestDto dto, Member member);
    TripMember saveTripMember(Trip trip , Member member);
    int issueCode(Long id,Member member);
    Trip joinTrip(int code,Member member);
    List<Trip> findAllTrip(Member member);

    List<Trip> findAllTripByCompleted(Member member);

    List<TripMember> findTripMembers(Trip trip);

}
