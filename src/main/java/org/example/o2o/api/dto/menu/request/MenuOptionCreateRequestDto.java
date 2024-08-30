package org.example.o2o.api.dto.menu.request;

import org.example.o2o.domain.menu.StoreMenuOption;

public record MenuOptionCreateRequestDto(
	Integer ordering,
	String name,
	String desc,
	Integer price
) {
	StoreMenuOption to() {
		return StoreMenuOption.builder()
			.ordering(ordering())
			.name(name())
			.description(desc())
			.price(price())
			.build();
	}
}
