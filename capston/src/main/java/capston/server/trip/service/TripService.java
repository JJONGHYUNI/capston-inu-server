package capston.server.trip.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.photo.service.PhotoService;
import capston.server.trip.domain.Trip;
import capston.server.trip.domain.TripMember;
import capston.server.trip.dto.TripSaveRequestDto;
import capston.server.trip.repository.TripMemberRepository;
import capston.server.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capston.server.exception.Code.SERVER_ERROR;
import static capston.server.exception.Code.TRIP_NOT_FOUND;

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
    public Trip saveTrip(TripSaveRequestDto dto,String token){
        Trip trip = save(dto.toEntity());
        Member member = memberService.findMember(token);
        saveTripMember(trip,member);
        if(dto.getFiles().size()!=0){
            photoService.savePhoto(trip,dto.getFiles());
        }
        return trip;
    }

    @Transactional
    public TripMember saveTripMember(Trip trip , Member member){
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
}
