package capston.server.trip.service;

import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberServiceImpl;
import capston.server.photo.service.PhotoServiceImpl;
import capston.server.trip.domain.Trip;
import capston.server.trip.domain.TripMember;
import capston.server.trip.dto.TripDefaultResponseDto;
import capston.server.trip.dto.TripNewSaveRequestDto;
import capston.server.trip.dto.TripSaveRequestDto;
import capston.server.trip.repository.TripMemberRepository;
import capston.server.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static capston.server.exception.Code.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TripServiceImpl implements TripService{
    private final TripRepository tripRepository;
    private final MemberServiceImpl memberService;
    private final TripMemberRepository tripMemberRepository;
    private final PhotoServiceImpl photoService;

    @Transactional
    @Override
    public Trip save(Trip trip){
        try{
            return tripRepository.save(trip);
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }
    @Override
    public Trip findTripById(Long id){
        try{
            return tripRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new CustomException(e,TRIP_NOT_FOUND);
        }
    }
    @Transactional
    @Override
    public Trip saveTrip(TripSaveRequestDto dto, String token){
        Trip trip = tripRepository.findById(dto.getTripId()).orElseThrow(()-> new CustomException(null,TRIP_NOT_FOUND));
        Member member = memberService.findMember(token);
        if(dto.getFiles().size()!=0){
            photoService.savePhoto(trip,dto.getFiles());
        }
        return trip;
    }
    @Transactional
    @Override
    public Trip newSaveTrip(TripNewSaveRequestDto dto,String token){
        Trip trip = save(dto.toEntity());
        Member member = memberService.findMember(token);
        saveTripMember(trip,member);
        return trip;
    }

    @Transactional
    @Override
    public TripMember saveTripMember(Trip trip , Member member){
        if (tripMemberRepository.findByTripAndMember(trip,member).isPresent()){
            return tripMemberRepository.findByTripAndMember(trip,member).get();
        }
        TripMember tripMember = TripMember.builder()
                .trip(trip)
                .member(member)
                .build();
        try{
            return tripMemberRepository.save(tripMember);
        }catch (RuntimeException e){
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    /**
     * 공유 코드 발급받기
     */
    @Transactional
    @Override
    public int issueCode(Long id){
        Trip trip = tripRepository.findById(id).orElseThrow(()->new CustomException(null,TRIP_NOT_FOUND));
        return trip.updateCode();
    }

    /**
     * 공유 코드로 여행 참가
     */
    @Transactional
    @Override
    public Trip joinTrip(int code,String token){
        Member member = memberService.findMember(token);
        Trip trip = tripRepository.findByCode(code).orElseThrow(()->new CustomException(null,TRIP_CODE_NOT_FOUND));
        saveTripMember(trip,member);
        return trip;
    }
    @Transactional
    @Override
    public List<TripDefaultResponseDto> findAllTrip(String token){
        Member member = memberService.findMember(token);
        List<TripMember> tripMembers= tripMemberRepository.findByMemberId(member.getId());
        List<Trip> trips = tripMembers.stream().map(tripMember -> tripMember.getTrip()).collect(Collectors.toList());
        List<TripDefaultResponseDto> result =trips.stream().map(trip -> new TripDefaultResponseDto(trip)).collect(Collectors.toList());
        return result;
    }

    @Override
    public boolean memberByJoinTrip(Member member , Trip trip){
        return tripMemberRepository.findByTripAndMember(trip,member).isPresent();
    }
}
