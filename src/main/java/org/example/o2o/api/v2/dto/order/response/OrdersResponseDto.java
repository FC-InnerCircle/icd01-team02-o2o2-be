package org.example.o2o.api.v2.dto.order.response;

import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.order.OrderStatus;

import lombok.Builder;

@Builder
public record OrdersResponseDto(
	Long orderId,
	OrderStatus status,
	String storeName,
	String ordererName,
	Integer menuTotalPrice
) {
	public static OrdersResponseDto of(OrderInfo order) {
		return OrdersResponseDto.builder()
			.orderId(order.getId())
			.status(order.getStatus())
			.storeName(order.getStore().getName())
			.ordererName(order.getMember().getName())
			.menuTotalPrice(order.getPrice())
			.build();
	}
}
