package org.example.o2o.api.dto.menu.request;

import java.util.ArrayList;
import java.util.Arrays;

import org.example.o2o.api.dto.file.request.ImageFileCreateRequestDto;
import org.example.o2o.domain.file.FileGroupType;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;

public record MenuCreateRequestDto(
	StoreMenuStatus status,
	String name,
	String desc,
	Integer price,
	Integer ordering,
	MenuOptionGroupCreateDto[] optionGroups,
	ImageFileCreateRequestDto[] images
) {
	public StoreMenu to() {
		StoreMenu menu = StoreMenu.builder()
			.ordering(ordering())
			.status(status())
			.name(name())
			.description(desc())
			.price(price())
			.menuOptionGroups(new ArrayList<>())
			.build();

		Arrays.stream(optionGroups()).forEach(optionGroup -> menu.addMenuOptionGroup(optionGroup.to()));
		menu.setImageFileGroup(ImageFileCreateRequestDto.createFileGroup(images(), FileGroupType.MENU));
		return menu;
	}
}
