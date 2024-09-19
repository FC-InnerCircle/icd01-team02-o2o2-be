package org.example.o2o.api.dto.order.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderOptionCreateRequestDto(
	@Schema(description = "메뉴 옵션 id", example = "1")
	@NotNull(message = "메뉴 옵션 id는 필수입니다.")
	Long optionId,

	@Schema(description = "옵션명", example = "맵기 1단계")
	@NotNull(message = "옵션명은 필수입니다.")
	String optionName,

	@Schema(description = "메뉴 옵션 그룹 id", example = "1000")
	@NotNull(message = "옵션 가격은 필수입니다.")
	@Min(value = 0, message = "옵션 가격은 최소 0원입니다.")
	Integer optionPrice) {
}
