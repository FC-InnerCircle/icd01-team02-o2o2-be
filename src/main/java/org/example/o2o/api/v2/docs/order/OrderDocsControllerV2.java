package org.example.o2o.api.v2.docs.order;

import org.example.o2o.api.v2.dto.order.request.OrdersRequestDto;
import org.example.o2o.api.v2.dto.order.response.OrderDetailResponseDto;
import org.example.o2o.api.v2.dto.order.response.OrdersResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface OrderDocsControllerV2 {

	@Operation(summary = "주문 상세 조회", description = "order id에 해당하는 주문 정보를 반환합니다.")
	@ApiResponse(responseCode = "200", description = "주문 상세 정보 반환")
	@ApiResponse(responseCode = "404", description = "유효하지 않은 주문입니다.")
	org.example.o2o.common.dto.ApiResponse<OrderDetailResponseDto> findById(@PathVariable Long orderId);

	@Operation(summary = "주문 목록 조회", description = "store id에 해당하는 주문 목록을 반환합니다.")
	@ApiResponse(responseCode = "200", description = "주문 목록 반환")
	@ApiResponse(responseCode = "400", description = "음식점을 찾을 수 없습니다.")
	org.example.o2o.common.dto.ApiResponse<OrdersResponseDto> findAll(@PathVariable Long storeId,
		OrdersRequestDto requestDto);
}
