package org.example.o2o.api.v1.dto.file.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileConfirmResponse {
	@Schema(description = "파일 ID", example = "1")
	private Long fileId;

	@Schema(description = "파일 존재 여부", example = "success")
	private String result;
}
