package capston.server.photo.service;

import capston.server.photo.dto.CommunicationRequsetDto;

public interface FlaskCommunicationService {
    String commnicateFlask(CommunicationRequsetDto dto);
    String communicateRestTemplate(Long tripId);
}
