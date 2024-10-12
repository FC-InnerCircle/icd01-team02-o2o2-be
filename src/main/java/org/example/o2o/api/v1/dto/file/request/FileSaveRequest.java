package org.example.o2o.api.v1.dto.file.request;

import org.example.o2o.common.dto.file.FileDto.PreSignedUrlRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileSaveRequest {
	@Schema(description = "회원 아이디", example = "test@test.com")
	@NotBlank(message = "회원 아이디는 필수 입력입니다.")
	private String memberId;

	@Schema(description = "파일 이름", example = "test.txt")
	@NotBlank(message = "파일 이름은 필수 입력입니다.")
	@JsonProperty("fileOriginName")
	private String fileName;

	@Schema(description = "파일 크기", example = "112233")
	@Positive(message = "파일 크기는 필수 입력입니다.")
	private Long fileSize;

	@Schema(description = "파일 확장자", example = "txt")
	@NotBlank(message = "파일 확장자는 필수 입력입니다.")
	private String fileExtension;

	public PreSignedUrlRequest toPreSignedUrlRequest() {
		return PreSignedUrlRequest.builder()
			.memberId(memberId)
			.originalFileName(fileName)
			.fileSize(fileSize)
			.fileExtension(fileExtension)
			.build();
	}

}
