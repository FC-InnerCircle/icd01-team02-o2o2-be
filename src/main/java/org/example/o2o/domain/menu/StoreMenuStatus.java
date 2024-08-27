package org.example.o2o.domain.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;

public enum StoreMenuStatus {
	SOLDOUT("품절"), DISABLED("숨김"), ENABLED("활성화");

	@Getter
	private final String text;

	StoreMenuStatus(String text) {
		this.text = text;
	}

	public static List<StoreMenuStatus> getMenuStatuses(String statusStr) {
		if (Objects.isNull(statusStr) || statusStr.isEmpty()) {
			return Arrays.asList(StoreMenuStatus.values());
		}

		return Arrays.stream(statusStr.split(","))
			.filter(status -> !status.isEmpty())
			.map(StoreMenuStatus::valueOf)
			.collect(Collectors.toList());
	}
}
