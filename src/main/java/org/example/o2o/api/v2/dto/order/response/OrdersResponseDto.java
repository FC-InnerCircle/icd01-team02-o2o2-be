package org.example.o2o.api.v2.dto.order.response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.order.OrderStatus;
import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
public record OrdersResponseDto(
	List<OrderResponseDto> orders,
	int page,
	int size,
	long totalLength
) {
	public static OrdersResponseDto of(Page<OrderInfo> orderPage) {
		return OrdersResponseDto.builder()
			.orders(orderPage.getContent()
				.stream()
				.map(OrderResponseDto::of)
				.collect(Collectors.toList()))
			.size(orderPage.getSize())
			.page(orderPage.getNumber())
			.totalLength(orderPage.getTotalElements())
			.build();
	}

	@Builder
	public record OrderResponseDto(
		Long orderId,
		OrderStatus status,
		String storeName,
		String ordererName,
		Integer menuTotalPrice
	) {
		public static OrderResponseDto of(OrderInfo order) {
			return OrderResponseDto.builder()
				.orderId(order.getId())
				.status(order.getStatus())
				.storeName(order.getStore().getName())
				.ordererName(order.getMember().getName())
				.menuTotalPrice(order.getPrice())
				.build();
		}
	}
}
