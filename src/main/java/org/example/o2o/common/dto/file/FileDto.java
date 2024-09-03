package org.example.o2o.common.dto.file;

import org.example.o2o.domain.file.FileDetail;

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

		public FileDetail toFileDetail(int ordering) {
			return FileDetail.builder()
				.ordering(ordering)
				.originalFileName(originalFileName)
				.storedFileName(storeFileName)
				.path(path)
				.extension(extension)
				.size(fileSize)
				.build();
		}
	}
}
