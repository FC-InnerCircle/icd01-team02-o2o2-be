package org.example.o2o.api.dto.file.request;

import java.util.Arrays;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.file.FileGroupType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ImageFileCreateRequestDto(
	@Schema(description = "이미지 정렬 순서", example = "1")
	@NotNull(message = "이미지 정렬 순서는 필수입니다.")
	Integer ordering,

	@Schema(description = "이미지 URL", example = "http://image.com")
	@NotNull(message = "이미지 URL은 필수입니다.")
	String imageUrl
) {
	FileDetail toFileDetail() {
		return FileDetail.builder()
			.ordering(ordering())
			.path(imageUrl())
			.build();
	}

	public static FileGroup createFileGroup(ImageFileCreateRequestDto[] images, FileGroupType type) {
		FileGroup fileGroup = FileGroup.builder()
			.groupType(type)
			.build();

		Arrays.stream(images).forEach(image -> fileGroup.addDetail(image.toFileDetail()));
		return fileGroup;
	}
}
