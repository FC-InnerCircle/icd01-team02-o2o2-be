package org.example.o2o.api.v2.controller.order;

import org.example.o2o.api.v2.docs.order.OrderDocsControllerV2;
import org.example.o2o.api.v2.dto.order.request.OrdersRequestDto;
import org.example.o2o.api.v2.dto.order.response.OrderDetailResponseDto;
import org.example.o2o.api.v2.dto.order.response.OrdersResponseDto;
import org.example.o2o.api.v2.service.order.OrderServiceV2;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class OrderControllerV2 implements OrderDocsControllerV2 {

	private final OrderServiceV2 orderServiceV2;

	@GetMapping("/orders/{orderId}")
	public ApiResponse<OrderDetailResponseDto> findById(@PathVariable Long orderId) {
		return ApiResponse.success(orderServiceV2.getOrderDetail(orderId));
	}

	@GetMapping("/stores/{storeId}/orders")
	public ApiResponse<OrdersResponseDto> findAll(@PathVariable Long storeId, OrdersRequestDto requestDto) {
		return ApiResponse.success(orderServiceV2.getOrderListByStoreId(storeId, requestDto));
	}
}
