package org.example.o2o.api.v1.dto.store.response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StoreMenuResponse(
	@Schema(description = "메뉴 ID", example = "1")
	long menuId,

	@Schema(description = "메뉴 상태", example = "ENABLED")
	StoreMenuStatus status,

	@Schema(description = "메뉴 이름", example = "떡볶이")
	String menuName,

	@Schema(description = "메뉴 설명", example = "고추장 쌀떡")
	String description,

	@Schema(description = "메뉴 가격", example = "14000")
	int menuPrice,

	@Schema(description = "메뉴 이미지 목록", example = "[]")
	String[] menuImages,

	@Schema(description = "메뉴 옵션 목록", example = "[]")
	List<StoreMenuOptionGroupResponse> optionGroups
) {

	public static StoreMenuResponse of(StoreMenu menu) {
		return StoreMenuResponse.builder()
			.menuId(menu.getId())
			.status(menu.getStatus())
			.menuName(menu.getName())
			.description(menu.getDescription())
			.menuPrice(menu.getPrice())
			.menuImages(
				menu.getImageFileGroup().getDetails().stream()
					.map(FileDetail::getFileAccessUrl)
					.toArray(String[]::new)
			)
			.optionGroups(
				menu.getMenuOptionGroups().stream()
					.map(StoreMenuOptionGroupResponse::of)
					.collect(Collectors.toList())
			)
			.build();
	}
}
