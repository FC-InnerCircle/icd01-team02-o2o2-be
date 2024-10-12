package org.example.o2o.domain.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreCategory {
	CAFE("카페"),
	CHICKEN("치킨"),
	KOREAN_FOOD("한식"),
	CHINESE_FOOD("중식"),
	SNACK_FOOD("간식"),
	WESTERN_FOOD("양식"),
	JAPANESE_FOOD("일식");

	private final String text;
}
