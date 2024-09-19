package org.example.o2o.api.docs.order;

import org.example.o2o.api.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.dto.order.response.OrderCreateResponseDto;
import org.example.o2o.config.exception.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "주문 API", description = "주문 생성/상태 변경/취소")
public interface OrderDocsController {

	@Operation(summary = "주문 생성", description = "주문을 생성합니다.")
	@ApiResponse(responseCode = "200", description = "주문 생성 뒤 storeId, orderId 반환")
	@ApiResponse(responseCode = "400", description = "잘못된 결제 수단",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "400", description = "잘못된 주문 정보",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<OrderCreateResponseDto> registerOrder(
		@RequestBody OrderCreateRequestDto requestDto);
}
