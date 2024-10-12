package org.example.o2o.api.v1.dto.order.response;

import org.example.o2o.domain.order.OrderInfo;

public record OrderCreateResponseDto(Long storeId, Long orderId) {

	public static OrderCreateResponseDto of(OrderInfo order) {
		return new OrderCreateResponseDto(order.getStore().getId(), order.getId());
	}
}
