package org.example.o2o.api.dto.menu.response;

import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;

import lombok.Builder;

@Builder
public record MenuResponseDto(
	long id,
	StoreMenuStatus status,
	String name,
	String desc,
	int price,
	String thumbImageUrl,
	int ordering
) {
	public static MenuResponseDto of(StoreMenu menu) {
		return MenuResponseDto.builder()
			.id(menu.getId())
			.status(menu.getStatus())
			.name(menu.getName())
			.desc(menu.getDescription())
			.price(menu.getPrice())
			.thumbImageUrl(menu.getThumbImageUrl())
			.ordering(menu.getOrdering())
			.build();
	}
}
