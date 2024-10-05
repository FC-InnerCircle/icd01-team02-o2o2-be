package org.example.o2o.domain.file;

import org.example.o2o.domain.AbstractEntity;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.persistence.Entity;
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
	private Long size;
	private String extension;
	private FileSyncStatus syncStatus;

	public enum FileSyncStatus {
		WAITING,    // 대기
		PROCESSING,    // 처리중
		DELETED,    // 삭제
		COMPLETED    // 완료
	}

	public String getFileAccessUrl() {
		return UriComponentsBuilder
			.fromHttpUrl(path)
			.path(storedFileName)
			.build().toUriString();
	}
}
