package org.example.o2o.api.v2.dto.order.response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.order.OrderStatus;
import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record OrdersResponseDto(
	@Schema(description = "주문 목록", example = "")
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
		@Schema(description = "주문 id", example = "1")
		Long orderId,

		@Schema(description = "주문 상태", example = "PENDING | ACCEPTED | PREPARING | DELIVERING | DELIVERED | CANCELED")
		OrderStatus status,

		@Schema(description = "가게명", example = "동대문 엽기 떡볶이")
		String storeName,

		@Schema(description = "주문자명", example = "에드워드 리")
		String ordererName,

		@Schema(description = "주문 총 가격", example = "14000")
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
