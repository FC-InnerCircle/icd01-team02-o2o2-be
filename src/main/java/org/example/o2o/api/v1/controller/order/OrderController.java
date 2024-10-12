package org.example.o2o.api.v1.controller.order;

import org.example.o2o.api.v1.docs.order.OrderDocsController;
import org.example.o2o.api.v1.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.v1.dto.order.response.OrderCreateResponseDto;
import org.example.o2o.api.v1.service.order.OrderService;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController implements OrderDocsController {

	private final OrderService orderService;

	@PostMapping("/order")
	public ApiResponse<OrderCreateResponseDto> registerOrder(OrderCreateRequestDto requestDto) {
		return ApiResponse.success(orderService.order(requestDto));
	}
}
