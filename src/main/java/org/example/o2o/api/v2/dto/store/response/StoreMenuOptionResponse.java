package org.example.o2o.api.v2.dto.store.response;

import org.example.o2o.domain.menu.StoreMenuOption;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StoreMenuOptionResponse(
	@Schema(description = "메뉴 옵션 항목 ID", example = "1")
	long optionId,

	@Schema(description = "메뉴 옵션 항목 가격", example = "1000")
	int optionPrice,

	@Schema(description = "메뉴 옵션 항목 값", example = "1단계")
	String optionName
) {
	public static StoreMenuOptionResponse of(StoreMenuOption option) {
		return StoreMenuOptionResponse.builder()
			.optionId(option.getId())
			.optionPrice(option.getPrice())
			.optionName(option.getName())
			.build();
	}
}
