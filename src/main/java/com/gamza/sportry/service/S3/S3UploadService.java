package com.gamza.sportry.service.S3;

import static com.gamza.sportry.core.error.ErrorCode.FORBIDDEN_EXCEPTION;

import com.gamza.sportry.core.error.ErrorCode;
import com.gamza.sportry.core.error.exception.BadRequestException;
import com.gamza.sportry.core.error.exception.BusinessException;
import com.gamza.sportry.core.error.exception.NotFoundException;
import com.gamza.sportry.core.error.exception.UnAuthorizedException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class S3UploadService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadFile(MultipartFile multipartFile) {
        validateFile(multipartFile); // 파일 검증 호출

        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            s3Client.putObject(new PutObjectRequest(bucket, s3FileName, inputStream, objMeta)
//                    .withCannedAcl(CannedAccessControlList.PublicRead)
                    );
        } catch (IOException e) {
            throw new BadRequestException("파일 업로드 중 문제가 발생했습니다", ErrorCode.PARAMETER_VALID_EXCEPTION);
        }

        return s3Client.getUrl(bucket, s3FileName).toString();
    }

    // 파일 유효성 검사
    public void validateFile(MultipartFile multipartFile) {
        // 파일이 비었는지 확인
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BadRequestException("업로드된 파일이 비어있습니다", ErrorCode.PARAMETER_VALID_EXCEPTION);
        }

        // 파일 이름 가져오기
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new BadRequestException("파일 이름이 유효하지 않습니다", ErrorCode.PARAMETER_VALID_EXCEPTION);
        }

        // 허용된 확장자 검사
        List<String> validExtensions = List.of(".jpg", ".jpeg", ".png");
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (!validExtensions.contains(fileExtension.toLowerCase())) {
            throw new BadRequestException("허용되지 않는 파일 형식입니다", ErrorCode.PARAMETER_VALID_EXCEPTION);
        }
    }


    // 업로드된 파일 Url 가져오기
    @Transactional
    public String getFileUrl(String fileName) {
        return s3Client.getUrl(bucket, fileName).toString();
    }

    // DeleteObject를 통해 S3 파일 삭제
    @Transactional
    public void deleteFile(String fileUrl) {
        log.info("deleteImage = {}", fileUrl);
        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileUrl));
    }
}