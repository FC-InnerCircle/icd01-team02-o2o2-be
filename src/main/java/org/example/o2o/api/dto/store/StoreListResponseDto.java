package org.example.o2o.api.dto.store;

import java.util.List;

import org.example.o2o.domain.store.Store;
import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StoreListResponseDto(
	@Schema(description = "스토어 상세 정보 리스트")
	List<StoreDetailResponseDto> stores,

	@Schema(description = "현재 페이지 번호", example = "1")
	int page,

	@Schema(description = "페이지 당 항목 수", example = "10")
	int size,

	@Schema(description = "총 항목 수", example = "100")
	long totalLength
) {
	public static StoreListResponseDto of(Page<Store> storePage) {
		return StoreListResponseDto.builder()
			.stores(storePage.getContent()
				.stream()
				.map(StoreDetailResponseDto::of)
				.toList())
			.page(storePage.getNumber())
			.size(storePage.getSize())
			.totalLength(storePage.getTotalElements())
			.build();

	}
}
