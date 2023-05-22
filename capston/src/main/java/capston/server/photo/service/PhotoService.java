package capston.server.photo.service;

import capston.server.exception.Code;
import capston.server.exception.CustomException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static capston.server.exception.Code.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {
    private final AmazonS3Client amazonS3Client;

    public void validateFileType(MultipartFile file){
        String type = file.getContentType().split("/")[1];
        if(!type.equals("jpg") && !type.equals("jpeg")&&!type.equals("png")){
            throw new CustomException(null, FILE_TYPE_UNSUPPORTED);
        }
    }

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
}
