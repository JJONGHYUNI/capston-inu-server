package capston.server.photo.service;

import capston.server.exception.CustomException;
import capston.server.member.domain.Member;
import capston.server.photo.domain.MemberPhoto;
import capston.server.photo.domain.Photo;
import capston.server.photo.repository.MemberPhotoRepository;
import capston.server.photo.repository.PhotoRepository;
import capston.server.trip.domain.Trip;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static capston.server.exception.Code.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PhotoServiceImpl implements PhotoService{
    private final AmazonS3Client amazonS3Client;
    private final PhotoRepository photoRepository;
    private final MemberPhotoRepository memberPhotoRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    /**
     * 파일 형식 검증(415)
     */
    @Override
    public void validateFileType(MultipartFile file){
        String type = file.getContentType().split("/")[1];
        if(!type.equals("jpg") && !type.equals("jpeg")&&!type.equals("png")){
            throw new CustomException(null, FILE_TYPE_UNSUPPORTED);
        }
    }
    /**
     * 파일 존재 여부 (404)
     */
    @Override
    public void existFile(MultipartFile file){
        if(file==null){
            throw new CustomException(null,FILE_NOT_FOUND);
        }
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException{
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream os=new FileOutputStream(file);
        os.write(multipartFile.getBytes());
        os.close();
        return file;
    }
    @Override
    public String insertFile(String bucketName, String folderName,String fileName,MultipartFile multipartFile){
        try{
            File file = convertMultiPartToFile(multipartFile);
            String uploadFileName = folderName+ "/" + fileName;

            amazonS3Client.putObject(new PutObjectRequest(bucketName,uploadFileName,file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            file.delete();
            String url = amazonS3Client.getUrl(bucketName,uploadFileName).toString();
            return url;
        }catch (IOException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }
    @Transactional
    @Override
    public void save(Photo photo){
        try{
            photoRepository.save(photo);
        }catch (RuntimeException e){
            throw new CustomException(e,SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public MemberPhoto save(MemberPhoto memberPhoto){
        try{
            return memberPhotoRepository.save(memberPhoto);
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public List<Photo> savePhoto(Trip trip, List<MultipartFile> files){
        String folderName = trip.getTitle() + "/" + trip.getId();
        List<Photo> savedPhotos = new ArrayList<>();
        for (int i =0; i<files.size();i++){
            MultipartFile file =files.get(i);
            validateFileType(file);
            String fileName = UUID.randomUUID() + file.getContentType().replace("image/",".");
            String url = insertFile(bucketName,folderName,fileName,file);
            Photo savedPhoto= null;
            try{
                savedPhoto = Photo.builder()
                        .trip(trip)
                        .photoUrl(url)
                        .build();
            }catch (RuntimeException e){
                throw new CustomException(e,SERVER_ERROR);
            }
            save(savedPhoto);
            savedPhotos.add(savedPhoto);
        }
        return savedPhotos;
    }

    @Override
    public MemberPhoto savePhoto(Member member, MultipartFile file) {
        String folderName = member.getName() + "/" + member.getId();
        existFile(file);
        validateFileType(file);
        String fileName = UUID.randomUUID() + file.getContentType().replace("image/", ".");
        String url = insertFile(bucketName, folderName, fileName, file);
        try{
            return save(MemberPhoto.builder()
                    .member(member)
                    .photoUrl(url)
                    .build());
        }catch (RuntimeException e){
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    @Override
    public List<String> findPhotoByTripId(Long tripId){
        List<String> photoUrls = new ArrayList<>();
        List<Photo> photos = photoRepository.findAllByTripId(tripId);
        photoUrls = photos.stream().map(Photo::getPhotoUrl).collect(Collectors.toList());
        return photoUrls;
    }

    @Override
    public List<String> findPhotoByTrip(Trip trip) {
        Page<Photo> photos = photoRepository.findAllByTrip(trip, PageRequest.of(0, 9));
        return photos.stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
    }
}
