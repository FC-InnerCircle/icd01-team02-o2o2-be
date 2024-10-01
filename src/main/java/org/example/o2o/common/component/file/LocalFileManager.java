package org.example.o2o.common.component.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.example.o2o.common.dto.file.FileDto.UploadFileResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.file.FileErrorCode;
import org.example.o2o.domain.file.ResourceLocation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Profile("local")
@Slf4j
@Component
public class LocalFileManager implements FileManager {

	private static final String UPLOAD_DIRECTORY = "upload/";

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

			// 디렉토리가 없는 경우 생성
			Path uploadDirectory = Paths.get(createUploadDirectory());
			if (Files.notExists(uploadDirectory)) {
				Files.createDirectories(uploadDirectory);
			}

			// 파일 메타 정보 추출
			String originalFilename = file.getOriginalFilename();
			String storeFileName = createStoreFileName(originalFilename);
			String extension = extractExt(originalFilename);
			String path = uploadDirectory.toString();
			Long fileSize = file.getSize();

			// 파일 업로드
			Path uploadFilePullPath = uploadDirectory.resolve(storeFileName);
			file.transferTo(uploadFilePullPath);

			return UploadFileResponse.builder()
				.storeFileName(storeFileName)
				.originalFileName(originalFilename)
				.fileSize(fileSize)
				.extension(extension)
				.path(path)
				.resourceLocation(ResourceLocation.LOCAL)
				.build();
		} catch (IOException e) {
			log.error("storeFile Exception: ", e);
			throw new ApiException(FileErrorCode.FILE_SAVE_FAIL);
		}
	}

	public String createUploadDirectory() {
		return UPLOAD_DIRECTORY;
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
