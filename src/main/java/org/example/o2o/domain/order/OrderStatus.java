package org.example.o2o.domain.order;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum OrderStatus {
	PENDING("주문 확인 중"),
	ACCEPTED("주문 수락"),
	PREPARING("메뉴 준비 중"),
	DELIVERING("배달 중"),
	DELIVERED("배달 완료"),
	CANCELED("주문 취소");

	private final String text;

	OrderStatus(String text) {
		this.text = text;
	}

	public static List<OrderStatus> getOrderStatuses(String statusStr) {
		if (Objects.isNull(statusStr) || statusStr.isEmpty()) {
			return Arrays.asList(OrderStatus.values());
		}

		return Arrays.stream(statusStr.split(","))
			.filter(status -> !status.isEmpty())
			.map(OrderStatus::valueOf)
			.collect(Collectors.toList());
	}
}
