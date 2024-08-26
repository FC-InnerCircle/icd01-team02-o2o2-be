package org.example.o2o.api.dto.menu;

import org.example.o2o.domain.menu.StoreMenuOption;

import lombok.Builder;

@Builder
public record MenuOptionResponseDto(
	long optionId,
	int ordering,
	int price,
	String name,
	String desc
) {
	public static MenuOptionResponseDto of(StoreMenuOption option) {
		return MenuOptionResponseDto.builder()
			.optionId(option.getId())
			.ordering(option.getOrdering())
			.price(option.getPrice())
			.name(option.getName())
			.desc(option.getDescription())
			.build();
	}
}
