package org.example.o2o.common.dto.file;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileTemporaryStorage;
import org.example.o2o.domain.file.ResourceLocation;
import org.springframework.web.util.UriComponentsBuilder;

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
		private ResourceLocation resourceLocation;

		public FileDetail toFileDetail(int ordering) {
			return FileDetail.builder()
				.ordering(ordering)
				.originalFileName(originalFileName)
				.storedFileName(storeFileName)
				.path(path)
				.extension(extension)
				.size(fileSize)
				.resourceLocation(resourceLocation)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PreSignedUrlRequest {
		private String memberId;
		private String originalFileName;
		private Long fileSize;
		private String fileExtension;
	}

	@ToString
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PreSignedUrlResponse {

		private String memberId;
		private String originalFileName;
		private String storedFileName;
		private String path;
		private String fullPath;
		private Long fileSize;
		private String fileExtension;
		private FileTemporaryStorage.FileSyncStatus syncStatus;

		public FileTemporaryStorage toFileTemporaryStorage() {
			return FileTemporaryStorage.builder()
				.memberId(memberId)
				.originalFileName(originalFileName)
				.storedFileName(storedFileName)
				.path(path)
				.fullPath(
					UriComponentsBuilder
						.fromHttpUrl(path)
						.path(storedFileName)
						.build().toUriString()
				)
				.size(fileSize)
				.extension(fileExtension)
				.syncStatus(syncStatus)
				.build();
		}
	}
}
