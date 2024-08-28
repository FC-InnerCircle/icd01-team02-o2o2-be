package org.example.o2o.api.dto.file.request;

import java.util.ArrayList;
import java.util.Arrays;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.file.FileGroupType;

public record ImageFileCreateRequestDto(Integer ordering, String imageUrl) {
	FileDetail to() {
		return FileDetail.builder()
			.ordering(ordering())
			.path(imageUrl())
			.build();
	}

	public static FileGroup createFileGroup(ImageFileCreateRequestDto[] images, FileGroupType type) {
		FileGroup fileGroup = FileGroup.builder()
			.groupType(type)
			.details(new ArrayList<>())
			.build();

		Arrays.stream(images).forEach(image -> fileGroup.addDetail(image.to()));
		return fileGroup;
	}
}
