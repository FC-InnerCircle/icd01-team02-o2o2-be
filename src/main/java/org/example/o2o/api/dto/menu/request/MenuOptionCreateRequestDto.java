package org.example.o2o.api.dto.menu.request;

import org.example.o2o.domain.menu.StoreMenuOption;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MenuOptionCreateRequestDto(
	@Schema(description = "메뉴 옵션 항목 정렬 순서", example = "1")
	@NotNull(message = "메뉴 옵션 항목 정렬 순서는 필수입니다.")
	Integer ordering,

	@Schema(description = "메뉴 옵션 항목 값", example = "1단계")
	@NotNull(message = "메뉴 옵션 항목 값은 필수입니다.")
	String name,

	@Schema(description = "메뉴 옵션 항목 설명", example = "신라면 정도 맵기")
	String desc,

	@Schema(description = "메뉴 옵션 항목 값", example = "1000")
	@Min(value = 0, message = "메뉴 옵션 가격은 최소 0원입니다.")
	Integer price
) {
	StoreMenuOption toStoreMenuOption() {
		return StoreMenuOption.builder()
			.ordering(ordering())
			.name(name())
			.price(price())
			.build();
	}
}
