package capston.server.photo.controller;

import capston.server.member.domain.Member;
import capston.server.member.service.MemberService;
import capston.server.photo.dto.AlbumResponseDto;
import capston.server.photo.dto.CommunicationRequsetDto;
import capston.server.photo.dto.PhotoByTripResponseDto;
import capston.server.photo.service.PhotoService;
import capston.server.trip.domain.Trip;
import capston.server.trip.service.TripService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "앨범 ")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    private final MemberService memberService;
    private final TripService tripService;
    private final PhotoService photoService;

    @GetMapping("/find")
    public ResponseEntity<List<AlbumResponseDto>> findMyAlbum(@RequestHeader("X-AUTH-TOKEN") String token) {
        Member member = memberService.findMember(token);
        List<Trip> trips = tripService.findTripMembersByPage(member);
        List<AlbumResponseDto> dtos = trips.stream()
                .map(trip -> new AlbumResponseDto(trip,photoService.findPhotoByTrip(trip)))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/find/{tripId}")
    public ResponseEntity<PhotoByTripResponseDto> findMyAlbumByTripId(@RequestHeader("X-AUTH-TOKEN") String token, @PathVariable Long tripId) {
        Member member = memberService.findMember(token);
        Trip trip = tripService.findTripById(tripId);
        return ResponseEntity.ok(new PhotoByTripResponseDto(trip));
    }
}
