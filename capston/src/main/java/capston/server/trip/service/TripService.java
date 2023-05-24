package capston.server.trip.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.photo.service.PhotoService;
import capston.server.trip.domain.Trip;
import capston.server.trip.dto.TripSaveRequestDto;
import capston.server.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static capston.server.exception.Code.SERVER_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TripService {
    private final TripRepository tripRepository;
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
    public Trip saveTrip(TripSaveRequestDto dto){
        Trip trip = save(dto.toEntity());
        log.info("{}",dto.getFiles().size());
        if(dto.getFiles().size()!=0){
            photoService.savePhoto(trip,dto.getFiles());
        }
        return trip;
    }
}
