package org.example.o2o.api.v1.controller.order;

import org.example.o2o.api.v1.docs.order.OrderDocsControllerV1;
import org.example.o2o.api.v1.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.v1.dto.order.response.OrderCreateResponseDto;
import org.example.o2o.api.v1.service.order.OrderServiceV1;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderControllerV1 implements OrderDocsControllerV1 {

	private final OrderServiceV1 orderServiceV1;

	@PostMapping("/order")
	public ApiResponse<OrderCreateResponseDto> registerOrder(OrderCreateRequestDto requestDto) {
		return ApiResponse.success(orderServiceV1.order(requestDto));
	}
}
