package org.example.o2o.common.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class FileDto {

	@ToString
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UploadFileResponse {
		private String originalFileName;
		private String storeFileName;
		private String path;
		private Long fileSize;
		private String extension;
	}
}
