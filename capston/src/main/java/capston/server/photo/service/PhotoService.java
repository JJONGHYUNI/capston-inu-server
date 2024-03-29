package capston.server.photo.service;

import capston.server.member.domain.Member;
import capston.server.photo.domain.MemberPhoto;
import capston.server.photo.domain.Photo;
import capston.server.trip.domain.Trip;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {
    void validateFileType(MultipartFile file);
    void existFile(MultipartFile file);
    String insertFile(String bucketName, String folderName,String fileName,MultipartFile multipartFile);
    void save(Photo photo);
    MemberPhoto save(MemberPhoto memberPhoto);
    List<Photo> savePhoto(Trip trip, List<MultipartFile> files);
    MemberPhoto savePhoto(Member member, MultipartFile file);
    List<String> findPhotoByTripId(Long tripId);
    List<String> findPhotoByTrip(Trip trip);
}
