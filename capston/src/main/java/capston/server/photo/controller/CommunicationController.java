package capston.server.photo.controller;

import capston.server.photo.dto.CommunicationRequsetDto;
import capston.server.photo.service.FlaskCommunicationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Flask 서버 통신")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/flask")
public class CommunicationController {
    private final FlaskCommunicationService flaskCommunicationService;

    @PostMapping("/communication")
    public ResponseEntity<String> communicateFlask(@RequestBody CommunicationRequsetDto dto){
        String res = flaskCommunicationService.commnicateFlask(dto);
        return ResponseEntity.ok(res);
    }
}
