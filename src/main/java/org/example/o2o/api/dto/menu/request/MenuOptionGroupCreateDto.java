package org.example.o2o.api.dto.menu.request;

import java.util.Arrays;

import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record MenuOptionGroupCreateDto(
	@Schema(description = "메뉴 옵션 정렬 순서", example = "1")
	@NotNull(message = "메뉴 옵션 정렬 순서는 필수입니다.")
	Integer ordering,

	@Schema(description = "메뉴 옵션명", example = "맵기 조절")
	@NotNull(message = "메뉴 옵션명은 필수입니다.")
	String title,

	@Schema(description = "메뉴 옵션 필수 여부", example = "true")
	@NotNull(message = "'메뉴 옵션 필수 여부'는 필수입니다.")
	Boolean isRequired,

	@Schema(description = "옵션 항목 목록", example = "[{ordering: 1, name: '1단계', desc: '', price: 0}]")
	@Length(min = 1, message = "옵션 항목은 하나 이상 등록되어야 합니다.")
	MenuOptionCreateRequestDto[] options
) {
	StoreMenuOptionGroup toStoreMenuOptionGroup() {
		StoreMenuOptionGroup optionGroup = StoreMenuOptionGroup.builder()
			.ordering(ordering())
			.title(title())
			.isRequired(isRequired())
			.build();

		Arrays.stream(options).forEach(option -> optionGroup.addMenuOption(option.toStoreMenuOption()));
		return optionGroup;
	}
}
