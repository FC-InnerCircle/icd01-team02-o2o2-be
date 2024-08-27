package org.example.o2o.api.dto.menu;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.api.dto.file.ImageFileResponseDto;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;

import lombok.Builder;

@Builder
public record MenuDetailResponseDto(
	long menuId,
	StoreMenuStatus status,
	String name,
	String desc,
	int price,
	List<ImageFileResponseDto> images,
	List<MenuOptionGroupResponseDto> optionGroups
) {

	public static MenuDetailResponseDto of(StoreMenu menu) {
		return MenuDetailResponseDto.builder()
			.menuId(menu.getId())
			.status(menu.getStatus())
			.name(menu.getName())
			.desc(menu.getDescription())
			.price(menu.getPrice())
			.images(menu.getImageFileGroup()
				.getDetails()
				.stream()
				.map(ImageFileResponseDto::of)
				.collect(Collectors.toList()))
			.optionGroups(menu.getMenuOptionGroups()
				.stream()
				.map(MenuOptionGroupResponseDto::of)
				.collect(Collectors.toList()))
			.build();
	}
}
