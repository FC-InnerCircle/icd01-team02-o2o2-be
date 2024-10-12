package org.example.o2o.api.v1.dto.menu.response;

import org.example.o2o.domain.menu.StoreMenuOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MenuOptionResponseDto(
	@Schema(description = "메뉴 옵션 항목 ID", example = "1")
	long optionId,

	@Schema(description = "메뉴 옵션 항목 정렬 순서", example = "1")
	int ordering,

	@Schema(description = "메뉴 옵션 항목 가격", example = "1000")
	int price,

	@Schema(description = "메뉴 옵션 항목 값", example = "1단계")
	String name
) {
	public static MenuOptionResponseDto of(StoreMenuOption option) {
		return MenuOptionResponseDto.builder()
			.optionId(option.getId())
			.ordering(option.getOrdering())
			.price(option.getPrice())
			.name(option.getName())
			.build();
	}
}
