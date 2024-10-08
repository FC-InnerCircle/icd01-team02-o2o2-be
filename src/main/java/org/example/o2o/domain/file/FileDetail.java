package org.example.o2o.domain.file;

import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.example.o2o.domain.AbstractEntity;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class FileDetail extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_group_id")
	private FileGroup fileGroup;

	private Integer ordering;
	private String originalFileName;
	private String storedFileName;
	private String path;
	private String extension;
	private Long size;
	private ResourceLocation resourceLocation;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public String getFileAccessUrl() {
		return switch (resourceLocation) {
			case S3 -> UriComponentsBuilder
				.fromHttpUrl(path)
				.path(storedFileName)
				.build().toUriString();
			case LOCAL -> Paths.get(path, storedFileName).toString();
		};
	}
}
