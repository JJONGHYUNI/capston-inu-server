package capston.server.photo.controller;

import capston.server.member.service.MemberService;
import capston.server.photo.service.PhotoService;
import capston.server.trip.service.TripService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "앨범 ")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/album")
public class AlbumController {
    private final MemberService memberService;
    private final TripService tripService;

    private final PhotoService photoService;

}
