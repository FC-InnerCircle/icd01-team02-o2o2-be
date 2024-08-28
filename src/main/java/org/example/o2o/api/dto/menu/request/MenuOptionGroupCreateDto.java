package org.example.o2o.api.dto.menu.request;

import java.util.ArrayList;
import java.util.Arrays;

import org.example.o2o.domain.menu.StoreMenuOptionGroup;

public record MenuOptionGroupCreateDto(
	Integer ordering,
	String title,
	Boolean isRequired,
	MenuOptionCreateRequestDto[] options
) {
	StoreMenuOptionGroup to() {
		StoreMenuOptionGroup optionGroup = StoreMenuOptionGroup.builder()
			.ordering(ordering())
			.title(title())
			.isRequired(isRequired())
			.options(new ArrayList<>())
			.build();

		Arrays.stream(options).forEach(option -> optionGroup.addMenuOption(option.to()));
		return optionGroup;
	}
}
