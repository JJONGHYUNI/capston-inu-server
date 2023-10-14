package capston.server.photo.controller;

import capston.server.photo.dto.CommunicationRequsetDto;
import capston.server.photo.service.FlaskCommunicationServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Flask 서버 통신 (테스트용)")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/flask")
public class CommunicationController {
    private final FlaskCommunicationServiceImpl flaskCommunicationService;

    @PostMapping("/communication")
    public ResponseEntity<String> communicateFlask(@RequestBody CommunicationRequsetDto dto){
        String res = flaskCommunicationService.commnicateFlask(dto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/communication/{tripId}")
    public ResponseEntity<String> communicateRestTemplate(@PathVariable Long tripId){
        flaskCommunicationService.communicateRestTemplate(tripId);
        return ResponseEntity.ok("??");
    }
}
