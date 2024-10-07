package org.example.o2o.api.dto.store.response;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.api.dto.menu.response.MenuOptionResponseDto;
import org.example.o2o.domain.menu.StoreMenuOption;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StoreMenuOptionGroupResponse(
	@Schema(description = "메뉴 옵션 ID", example = "1")
	long optionGroupId,

	@Schema(description = "메뉴 옵션 필수 여부", example = "true")
	boolean isRequired,

	@Schema(description = "메뉴 옵션 다중선택 여부", example = "true")
	boolean isMultiple,

	@Schema(description = "메뉴 옵션명", example = "맵기 조절")
	String optionGroupName,

	@Schema(description = "메뉴 옵션 항목 목록", example = "[]")
	List<MenuOptionResponseDto> options
) {
	public static StoreMenuOptionGroupResponse of(
		StoreMenuOptionGroup optionGroup) {
		return StoreMenuOptionGroupResponse.builder()
			.optionGroupId(optionGroup.getId())
			.isRequired(optionGroup.getIsRequired())
			.isMultiple(optionGroup.getIsMultiple())
			.optionGroupName(optionGroup.getTitle())
			.options(optionGroup.getOptions()
				.stream()
				.sorted(Comparator.comparingInt(StoreMenuOption::getPrice)) // TODO ordering으로 정렬
				.map(MenuOptionResponseDto::of)
				.collect(Collectors.toList()))
			.build();
	}
}
