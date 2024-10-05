package org.example.o2o.api.dto.file.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRemoveResponse {
	@Schema(description = "파일 ID", example = "1")
	private Long fileId;
}
