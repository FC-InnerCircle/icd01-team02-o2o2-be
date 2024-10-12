package org.example.o2o.api.v1.dto.order.request;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderOptionGroupCreateRequestDto(
	@Schema(description = "메뉴 옵션 그룹 id", example = "1")
	@NotNull(message = "메뉴 옵션 그룹 id는 필수입니다.")
	Long optionGroupId,

	@Schema(description = "옵션 그룹명", example = "맵기 조절")
	@NotNull(message = "옵션 그룹명은 필수입니다.")
	String optionName,

	@Schema(description = "옵션 항목 목록", example = "[]")
	@NotNull(message = "옵션 항목은 필수입니다.")
	@Length(min = 1, message = "옵션 항목은 하나 이상 선택되어야 합니다.")
	OrderOptionCreateRequestDto[] options) {
}
