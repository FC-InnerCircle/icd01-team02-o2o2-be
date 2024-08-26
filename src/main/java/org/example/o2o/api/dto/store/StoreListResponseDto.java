package org.example.o2o.api.dto.store;

import java.util.List;

import org.example.o2o.domain.store.Store;
import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record StoreListResponseDto(
	List<StoreDetailResponseDto> stores,
	int page,
	int size,
	long totalLength
) {
	public static StoreListResponseDto of(Page<Store> storePage) {
		return StoreListResponseDto.builder()
			.stores(storePage.getContent()
				.stream()
				.map(StoreDetailResponseDto::of)
				.toList())
			.page(storePage.getNumber() + 1)
			.size(storePage.getSize())
			.totalLength(storePage.getTotalElements())
			.build();

	}
}
