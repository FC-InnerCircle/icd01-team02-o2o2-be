package org.example.o2o.api.dto.order.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderMenuCreateRequestDto(
	@Schema(description = "메뉴 id", example = "1")
	@NotNull(message = "메뉴 id는 필수입니다.")
	Long menuId,

	@Schema(description = "메뉴명", example = "떡볶이")
	@NotNull(message = "메뉴명은 필수입니다.")
	String menuName,

	@Schema(description = "메뉴 수량", example = "1")
	@NotNull(message = "메뉴 수량은 필수입니다.")
	@Min(value = 1, message = "메뉴 수량은 최소 1개입니다.")
	Integer menuCount,

	@Schema(description = "메뉴 가격", example = "15000")
	@NotNull(message = "메뉴 가격은 필수 입니다.")
	@Min(value = 0, message = "메뉴 가격은 최소 0원입니다.")
	Integer menuPrice,

	@Schema(description = "옵션 그룹 목록", example = "[]")
	OrderOptionGroupCreateRequestDto[] optionGroups) {
}
