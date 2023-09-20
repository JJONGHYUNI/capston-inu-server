package capston.server.photo.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.photo.domain.Photo;
import capston.server.photo.dto.CommunicationRequsetDto;
import capston.server.trip.domain.Trip;
import capston.server.trip.repository.TripMemberRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FlaskCommunicationServiceImpl implements FlaskCommunicationService{
    @Value("${spring.communicate.flask.url}")
    private String url;
    private final PhotoService photoService;

    private final TripMemberRepository tripMemberRepository;

    @Override
    public String commnicateFlask(CommunicationRequsetDto dto) {
        JsonObject json = new JsonObject();
        json.addProperty("num", dto.getNum());
        json.addProperty("photoNum", dto.getPhotoNum());
        JsonArray photoList = new JsonArray();
        for (String photo : dto.getPhotoList()) {
            photoList.add(photo);
        }
        json.add("photoList", photoList);
        String res = json.toString();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(res, "UTF-8");
        httpPost.setEntity(entity);
        try {
            CloseableHttpResponse response = client.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                return "성공";
            } else {
                return "실패";
            }
        } catch (IOException e) {
            throw new CustomException(null, Code.SERVER_ERROR);
        }
    }

    /**
     * RestTemplate를 이용해 요청을 보내고 AI가 선택해준 리턴 값 기다리기
     */
    @Override
    public String communicateRestTemplate(Long tripId){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(setImageToJson(tripId),setJsonHeader());
        log.info("여기까진 성공");
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url,HttpMethod.POST,requestEntity,String.class);
            log.info("여기까진 성공");
            HttpStatus status = responseEntity.getStatusCode();
            log.info("여기까진 성공");
            String responseBody = responseEntity.getBody();
            log.info("여기까진 성공");

            log.info("{}",status);
            log.info("{}",responseBody);
            return responseBody;
        }catch (RuntimeException e){
            throw new CustomException(null,Code.SERVER_ERROR);
        }
    }

    /**
     * header를 json 으로 설정
     */
    private HttpHeaders setJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 보내야할 이미지 정보 json으로 리턴
     */
    private String setImageToJson(Long tripId){
        JsonObject json= new JsonObject();
        List<String> photoUrls = photoService.findPhotoByTripId(tripId);
        int num = tripMemberRepository.findAllByTripId(tripId).size();
        json.addProperty("num",num);
        json.addProperty("photoNum",photoUrls.size());
        JsonArray photoList = new JsonArray();
        for(String photo : photoUrls){
            photoList.add(photo);
        }
        json.add("photoList",photoList);
        return json.toString();
    }

}
