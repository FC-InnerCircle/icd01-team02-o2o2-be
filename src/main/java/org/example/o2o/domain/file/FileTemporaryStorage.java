package org.example.o2o.domain.file;

import org.example.o2o.domain.AbstractEntity;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class FileTemporaryStorage extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String memberId;
	private String originalFileName;
	private String storedFileName;
	private String path;
	private String fullPath;
	private Long size;
	private String extension;

	@Enumerated(EnumType.STRING)
	private FileSyncStatus syncStatus;

	public enum FileSyncStatus {
		WAITING,    // 대기
		PROCESSING,    // 처리중
		DELETED,    // 삭제
		COMPLETED    // 완료
	}

	public void completeFileSync() {
		this.syncStatus = FileSyncStatus.COMPLETED;
	}

	public String getFileAccessUrl() {
		return UriComponentsBuilder
			.fromHttpUrl(path)
			.path(storedFileName)
			.build().toUriString();
	}

	public FileDetail toFileDetail(int ordering) {
		return FileDetail.builder()
			.ordering(ordering)
			.originalFileName(originalFileName)
			.storedFileName(storedFileName)
			.path(path)
			.extension(extension)
			.size(size)
			.resourceLocation(ResourceLocation.S3)
			.build();
	}
}
