package org.example.o2o.api.dto.order.request;

import java.util.List;

import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.order.OrderPaymentType;
import org.example.o2o.domain.order.OrderStatus;
import org.example.o2o.domain.store.Store;

import lombok.Builder;

@Builder
public record OrderCreateRequestDto(String memberId, Long storeId, String storeName, OrderMenuCreateRequestDto[] menus,
									Integer orderPrice, String payment, Long addressId) {

	public OrderInfo toOrder(Member member, Store store, Address address) {
		return OrderInfo.builder()
			.store(store)
			.member(member)
			.price(orderPrice)
			.menuDetail(List.of(menus))
			.address(address)
			.status(OrderStatus.PENDING)
			.payment(OrderPaymentType.toPaymentType(payment))
			.build();
	}
}
