package org.example.o2o.api.v1.controller.order;

import org.example.o2o.api.v1.docs.order.OrderDocsControllerV1;
import org.example.o2o.api.v1.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.v1.dto.order.response.OrderCreateResponseDto;
import org.example.o2o.api.v1.dto.order.response.OrderDetailResponseDto;
import org.example.o2o.api.v1.dto.order.response.OrdersResponseDto;
import org.example.o2o.api.v1.service.order.OrderServiceV1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderControllerV1 implements OrderDocsControllerV1 {

	private final OrderServiceV1 orderServiceV1;

	@PostMapping("/orders")
	public OrderCreateResponseDto registerOrder(OrderCreateRequestDto requestDto) {
		return orderServiceV1.order(requestDto);
	}

	@GetMapping("/orders/{orderId}")
	public OrderDetailResponseDto findById(@PathVariable Long orderId) {
		return orderServiceV1.getOrderDetail(orderId);
	}

	@GetMapping("/orders")
	public OrdersResponseDto findAll(Long memberId) {
		return orderServiceV1.getOrdersByMemberId(memberId);
	}
}
