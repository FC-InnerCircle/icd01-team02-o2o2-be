package org.example.o2o.api.dto.menu.response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.menu.StoreMenuOptionGroup;

import lombok.Builder;

@Builder
public record MenuOptionGroupResponseDto(
	long optionGroupId,
	int ordering,
	boolean isRequired,
	String title,
	List<MenuOptionResponseDto> options
) {
	public static MenuOptionGroupResponseDto of(StoreMenuOptionGroup optionGroup) {
		return MenuOptionGroupResponseDto.builder()
			.optionGroupId(optionGroup.getId())
			.ordering(optionGroup.getOrdering())
			.isRequired(optionGroup.getIsRequired())
			.title(optionGroup.getName())
			.options(optionGroup.getOptions()
				.stream()
				.map(MenuOptionResponseDto::of)
				.collect(Collectors.toList()))
			.build();
	}
}
