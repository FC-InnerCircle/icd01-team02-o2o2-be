package org.example.o2o.api.v2.dto.menu.response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.menu.StoreMenu;
import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MenusResponseDto(
	@Schema(description = "메뉴 목록", example = "[]")
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
