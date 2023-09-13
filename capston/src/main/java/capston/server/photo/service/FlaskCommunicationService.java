package capston.server.photo.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import capston.server.photo.dto.CommunicationRequsetDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FlaskCommunicationService {
    private final static String url = "http://13.124.231.93:5000/upload";
    private final PhotoService photoService;

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
    public void communicateRestTemplate(Long tripId){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<String> photoUrls = photoService.findPhotoByTripId(tripId);
        JsonObject json= new JsonObject();
        json.addProperty("num",3);
        json.addProperty("photoNum",photoUrls.size());
        JsonArray photoList = new JsonArray();
        for(String photo : photoUrls){
            photoList.add(photo);
        }
        json.add("photoList",photoList);
        String res = json.toString();
        HttpEntity<String> requestEntity = new HttpEntity<>(res,headers);
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
        }catch (RuntimeException e){
            throw new CustomException(null,Code.SERVER_ERROR);
        }
    }

}
