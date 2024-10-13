package org.example.o2o.api.v1.docs.file;

import org.example.o2o.api.v1.dto.file.request.FileSaveRequest;
import org.example.o2o.api.v1.dto.file.response.FileConfirmResponse;
import org.example.o2o.api.v1.dto.file.response.FileRemoveResponse;
import org.example.o2o.api.v1.dto.file.response.FileSaveResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface FileDocsControllerV1 {

	@Operation(summary = "S3 파일 업로드 요청", description = "pre-signed URL을 발급 요청합니다.")
	@ApiResponse(responseCode = "200", description = "임시 파일 정보 반환")
	org.example.o2o.common.dto.ApiResponse<FileSaveResponse> generatePreSignedUrl(
		@RequestBody FileSaveRequest fileSaveRequest);

	@Operation(summary = "S3 파일 상태 확인", description = "pre-signed URL의 파일이 존재하는지 확인합니다. (파일 업로드 후 성공/실패 확인)")
	@ApiResponse(responseCode = "200", description = "파일 업로드 결과 반환")
	@ApiResponse(responseCode = "400", description = "파일 정보를 찾을 수 없습니다. (이미 삭제되었거나, 정보가 누락된 경우)")
	org.example.o2o.common.dto.ApiResponse<FileConfirmResponse> confirmPreSignedUrl(@PathVariable Long fileId);

	@Operation(summary = "S3 파일 삭제", description = "pre-signed URL 정보와 임시 파일 정보를 삭제합니다. (업로드 실패, 취소 시)")
	@ApiResponse(responseCode = "200", description = "삭제된 파일 ID 반환")
	@ApiResponse(responseCode = "400", description = "파일 정보를 찾을 수 없습니다. (이미 삭제되었거나, 정보가 누락된 경우)")
	org.example.o2o.common.dto.ApiResponse<FileRemoveResponse> deleteFile(@PathVariable Long fileId);
}
