package org.example.o2o.api.v2.dto.menu.response;

import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MenuResponseDto(
	@Schema(description = "메뉴 id", example = "1")
	long id,

	@Schema(description = "메뉴 상태", example = "SOLDOUT | DISABLED | ENABLED")
	StoreMenuStatus status,

	@Schema(description = "메뉴명", example = "떡볶이")
	String name,

	@Schema(description = "메뉴 설명", example = "밀로 만든 떡볶이")
	String desc,

	@Schema(description = "메뉴 가격", example = "10000")
	int price,

	@Schema(description = "이미지 url", example = "")
	String thumbImageUrl,

	@Schema(description = "메뉴 정렬 순서", example = "1")
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
