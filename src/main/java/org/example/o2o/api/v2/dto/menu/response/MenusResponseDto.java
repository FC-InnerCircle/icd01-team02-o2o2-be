package org.example.o2o.api.v2.dto.menu.response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.menu.StoreMenu;
import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record MenusResponseDto(
	List<MenuResponseDto> menus,
	int page,
	int size,
	long totalLength
) {
	public static MenusResponseDto of(Page<StoreMenu> menuPage) {
		return MenusResponseDto.builder()
			.menus(menuPage.getContent()
				.stream()
				.map(MenuResponseDto::of)
				.collect(Collectors.toList()))
			.size(menuPage.getSize())
			.page(menuPage.getNumber())
			.totalLength(menuPage.getTotalElements())
			.build();
	}
}
