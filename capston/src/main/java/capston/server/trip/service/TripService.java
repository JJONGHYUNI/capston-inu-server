package capston.server.trip.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.photo.service.PhotoService;
import capston.server.trip.domain.Trip;
import capston.server.trip.domain.TripMember;
import capston.server.trip.dto.TripNewSaveRequestDto;
import capston.server.trip.dto.TripSaveRequestDto;
import capston.server.trip.repository.TripMemberRepository;
import capston.server.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static capston.server.exception.Code.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TripService {
    private final TripRepository tripRepository;
    private final MemberService memberService;
    private final TripMemberRepository tripMemberRepository;
    private final PhotoService photoService;

    @Transactional
    public Trip save(Trip trip){
        try{
            return tripRepository.save(trip);
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }
    @Transactional
    public Trip findTripById(Long id){
        try{
            return tripRepository.findById(id).get();
        }catch (RuntimeException e){
            throw new CustomException(e,TRIP_NOT_FOUND);
        }
    }
    @Transactional
    public Trip saveTrip(TripSaveRequestDto dto, String token){
        Trip trip = tripRepository.findById(dto.getTripId()).orElseThrow(()-> new CustomException(null,TRIP_NOT_FOUND));
        Member member = memberService.findMember(token);
        try{
            trip.updateTitle(dto.getTitle());
            trip.updateLocation(dto.getLocation());
            trip.updateMainPhoto(dto.getMainPhoto());
        }catch (RuntimeException e){
            throw new CustomException(null,SERVER_ERROR);
        }
        if(dto.getFiles().size()!=0){
            photoService.savePhoto(trip,dto.getFiles());
        }
        return trip;
    }
    @Transactional
    public Trip newSaveTrip(TripNewSaveRequestDto dto,String token){
        Trip trip = save(dto.toEntity());
        Member member = memberService.findMember(token);
        saveTripMember(trip,member);
        return trip;
    }

    @Transactional
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
    public int issueCode(Long id){
        Trip trip = tripRepository.findById(id).orElseThrow(()->new CustomException(null,TRIP_NOT_FOUND));
        return trip.updateCode();
    }

    /**
     * 공유 코드로 여행 참가
     */
    @Transactional
    public Trip joinTrip(int code,String token){
        Member member = memberService.findMember(token);
        Trip trip = tripRepository.findByCode(code).orElseThrow(()->new CustomException(null,TRIP_CODE_NOT_FOUND));
        saveTripMember(trip,member);
        return trip;
    }
}
