package org.example.o2o.api.v1.service.file;

import org.example.o2o.api.v1.dto.file.request.FileSaveRequest;
import org.example.o2o.api.v1.dto.file.response.FileConfirmResponse;
import org.example.o2o.api.v1.dto.file.response.FileRemoveResponse;
import org.example.o2o.api.v1.dto.file.response.FileSaveResponse;
import org.example.o2o.common.component.file.CloudFileManager;
import org.example.o2o.common.dto.file.FileDto.PreSignedUrlResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.file.FileErrorCode;
import org.example.o2o.domain.file.FileTemporaryStorage;
import org.example.o2o.repository.file.FileDetailRepository;
import org.example.o2o.repository.file.FileGroupRepository;
import org.example.o2o.repository.file.FileTemporaryStorageRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

	private final CloudFileManager cloudFileManager;
	private final FileGroupRepository fileGroupRepository;
	private final FileDetailRepository fileDetailRepository;
	private final FileTemporaryStorageRepository fileTemporaryStorageRepository;

	/**
	 * pre-signed URL 발급 요청
	 * @param request pre-signed URL 요청 정보(파일 메타데이터)
	 * @return
	 */
	public FileSaveResponse generatePreSignedUrl(FileSaveRequest request) {
		// pre-signed URL 발급 요청
		PreSignedUrlResponse preSignedUrl = cloudFileManager.generatePreSignedUrl(request.toPreSignedUrlRequest());

		// DB(파일 임시 저장소)에 정보 저장
		FileTemporaryStorage savedFileTemp = fileTemporaryStorageRepository.save(preSignedUrl.toFileTemporaryStorage());

		return FileSaveResponse.of(savedFileTemp);
	}

	/**
	 * pre-signed URL 파일 상태 확인
	 * @param fileId 임시 파일 저장 ID
	 */
	public FileConfirmResponse confirmPreSignedUrl(Long fileId) {
		FileTemporaryStorage fileTemp = fileTemporaryStorageRepository.findById(fileId)
			.orElseThrow(() -> new ApiException(FileErrorCode.NOT_FOUND));

		boolean existsFile = cloudFileManager.isExistsFile(fileTemp.getStoredFileName());

		return FileConfirmResponse.builder()
			.fileId(fileId)
			.result(existsFile ? "success" : "false")
			.build();
	}

	/**
	 * pre-signed URL 파일 삭제
	 * @param fileId 임시 파일 저장 ID
	 */
	public FileRemoveResponse deleteFile(Long fileId) {
		FileTemporaryStorage fileTemp = fileTemporaryStorageRepository.findById(fileId)
			.orElseThrow(() -> new ApiException(FileErrorCode.NOT_FOUND));

		// S3 파일 삭제
		cloudFileManager.deleteFile(fileTemp.getStoredFileName());

		// DB 임시 파일 정보 삭제
		fileTemporaryStorageRepository.delete(fileTemp);

		return new FileRemoveResponse(fileId);
	}
}
