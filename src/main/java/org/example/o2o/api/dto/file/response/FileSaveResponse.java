package org.example.o2o.api.dto.file.response;

import org.example.o2o.domain.file.FileTemporaryStorage;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileSaveResponse {

	@Schema(description = "파일 ID", example = "1")
	private Long fileId;

	@Schema(description = "원본 파일명", example = "test.txt")
	private String fileOriginName;

	@Schema(description = "저장된 파일명", example = "01dc0495-9643-4ecc-9184-0747e912cd42.txt")
	@JsonProperty("fileName")
	private String storedFileName;

	@Schema(description = "파일 접근 URL", example = "https://xxx.com/01dc0495-9643-4ecc-9184-0747e912cd42.txt")
	private String fileUrl;

	public static FileSaveResponse of(FileTemporaryStorage fileTemporaryStorage) {
		return FileSaveResponse.builder()
			.fileId(fileTemporaryStorage.getId())
			.fileOriginName(fileTemporaryStorage.getOriginalFileName())
			.storedFileName(fileTemporaryStorage.getStoredFileName())
			.fileUrl(fileTemporaryStorage.getFileAccessUrl())
			.build();
	}
}
