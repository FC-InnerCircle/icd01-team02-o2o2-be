package org.example.o2o.api.v1.dto.menu.request;

import java.util.Arrays;

import org.example.o2o.api.v1.dto.file.request.ImageFileCreateRequestDto;
import org.example.o2o.domain.file.FileGroupType;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MenuCreateRequestDto(
	@Schema(description = "메뉴 상태", example = "ENABLED")
	@Pattern(regexp = "^(SOLDOUT|DISABLED|ENABLED)$", message = "메뉴 상태는 'SOLDOUT', 'DISABLED', 'ENABLED'만 가능합니다.")
	StoreMenuStatus status,

	@Schema(description = "메뉴 이름", example = "떡볶이")
	@NotNull(message = "메뉴 이름은 필수입니다.")
	String name,

	@Schema(description = "메뉴 설명", example = "고추장 쌀 떡볶이")
	@NotNull(message = "메뉴 설명은 필수입니다.")
	String desc,

	@Schema(description = "메뉴 가격", example = "14000")
	@Min(value = 0, message = "메뉴 가겨은 최소 0원입니다.")
	Integer price,

	@Schema(description = "메뉴 정렬 순서", example = "1")
	@NotNull(message = "정렬 순서는 필수입니다.")
	Integer ordering,

	@Schema(
		description = "메뉴 옵션 그룹",
		example = "[{"
			+ "ordering: 1, title: '맵기', isRequired: true, options: []"
			+ "}]")
	MenuOptionGroupCreateRequestDto[] optionGroups,

	@Schema(description = "메뉴 이미지 목록", example = "[{ordering: 1, imageUrl: ''}, {ordering: 2, imageUrl: ''}]")
	@Length(min = 1, message = "이미지는 하나 이상 등록 되어야 합니다.")
	ImageFileCreateRequestDto[] images
) {
	public StoreMenu toStoreMenu() {
		StoreMenu menu = StoreMenu.builder()
			.ordering(ordering())
			.status(status())
			.name(name())
			.description(desc())
			.price(price())
			.build();

		Arrays.stream(optionGroups())
			.forEach(optionGroup -> menu.addMenuOptionGroup(optionGroup.toStoreMenuOptionGroup()));
		menu.setImageFileGroup(ImageFileCreateRequestDto.createFileGroup(images(), FileGroupType.MENU));
		return menu;
	}
}
