package org.example.o2o.api.dto.menu.response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.menu.StoreMenuOption;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MenuOptionGroupResponseDto(
	@Schema(description = "메뉴 옵션 ID", example = "1")
	long optionGroupId,

	@Schema(description = "메뉴 옵션 정렬 순서", example = "1")
	int ordering,

	@Schema(description = "메뉴 옵션 필수 여부", example = "true")
	boolean isRequired,

	@Schema(description = "메뉴 옵션 다중선택 여부", example = "true")
	boolean isMultiple,

	@Schema(description = "메뉴 옵션명", example = "맵기 조절")
	String title,

	@Schema(description = "메뉴 옵션 항목 목록", example = "[]")
	List<MenuOptionResponseDto> options
) {
	public static MenuOptionGroupResponseDto of(StoreMenuOptionGroup optionGroup) {
		return MenuOptionGroupResponseDto.builder()
			.optionGroupId(optionGroup.getId())
			.ordering(optionGroup.getOrdering())
			.isRequired(optionGroup.getIsRequired())
			.isMultiple(optionGroup.getIsMultiple())
			.title(optionGroup.getTitle())
			.options(optionGroup.getOptions()
				.stream()
				.sorted(Comparator.comparingInt(StoreMenuOption::getPrice)) // TODO ordering으로 정렬
				.map(MenuOptionResponseDto::of)
				.collect(Collectors.toList()))
			.build();
	}
}
