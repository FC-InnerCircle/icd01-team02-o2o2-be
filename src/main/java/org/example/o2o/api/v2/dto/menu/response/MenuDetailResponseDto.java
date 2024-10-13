package org.example.o2o.api.v2.dto.menu.response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.api.v1.dto.file.response.ImageFileResponseDto;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MenuDetailResponseDto(
	@Schema(description = "메뉴 ID", example = "1")
	long menuId,

	@Schema(description = "메뉴 상태", example = "ENABLED")
	StoreMenuStatus status,

	@Schema(description = "메뉴 이름", example = "떡볶이")
	String name,

	@Schema(description = "메뉴 설명", example = "고추장 쌀떡")
	String desc,

	@Schema(description = "메뉴 가격", example = "14000")
	int price,

	@Schema(description = "메뉴 이미지 목록", example = "[]")
	List<ImageFileResponseDto> images,

	@Schema(description = "메뉴 옵션 목록", example = "[]")
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
