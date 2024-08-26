package org.example.o2o.api.dto.file;

import org.example.o2o.domain.file.FileDetail;

public record ImageFileResponseDto(long imageId, int ordering, String imageUrl) {
	public static ImageFileResponseDto of(FileDetail detail) {
		return new ImageFileResponseDto(detail.getId(), detail.getOrdering(), detail.getPath());
	}
}
