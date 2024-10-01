package org.example.o2o.common.component.file;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.example.o2o.common.dto.file.FileDto.UploadFileResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.file.FileErrorCode;
import org.example.o2o.domain.file.ResourceLocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Profile("prod")
@Slf4j
@RequiredArgsConstructor
@Component
public class S3FileManager implements FileManager {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Override
	public List<UploadFileResponse> storeFiles(List<MultipartFile> files) {
		List<MultipartFile> validFiles = files.stream()
			.filter(file -> !file.isEmpty())
			.toList();

		if (validFiles.isEmpty()) {
			return null;
		}

		return validFiles.stream()
			.map(this::storeFile)
			.toList();
	}

	@Override
	public UploadFileResponse storeFile(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return null;
			}

			// 파일 메타 정보 추출
			String originalFilename = file.getOriginalFilename();
			String storeFileName = createStoreFileName(originalFilename);
			String extension = extractExt(originalFilename);

			String contentType = file.getContentType();
			Long fileSize = file.getSize();

			// 파일 업로드
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(fileSize);
			metadata.setContentType(contentType);

			amazonS3.putObject(bucket, storeFileName, file.getInputStream(), metadata);
			String fullPath = amazonS3.getUrl(bucket, storeFileName).toString();
			String path = fullPath.substring(0, fullPath.lastIndexOf("/"));

			return UploadFileResponse.builder()
				.storeFileName(storeFileName)
				.originalFileName(originalFilename)
				.fileSize(fileSize)
				.extension(extension)
				.path(path)
				.resourceLocation(ResourceLocation.S3)
				.build();
		} catch (IOException e) {
			log.error("storeFile Exception: ", e);
			throw new ApiException(FileErrorCode.FILE_SAVE_FAIL);
		}
	}

	private String createStoreFileName(String originalFilename) {
		String ext = extractExt(originalFilename);
		String uuid = UUID.randomUUID().toString();
		return uuid + "." + ext;
	}

	private String extractExt(String originalFilename) {
		int pos = originalFilename.lastIndexOf(".");
		return originalFilename.substring(pos + 1);
	}
}
