package org.example.o2o.api.v1.docs.order;

import org.example.o2o.api.v1.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.v1.dto.order.response.OrderCreateResponseDto;
import org.example.o2o.api.v1.dto.order.response.OrderDetailResponseDto;
import org.example.o2o.api.v1.dto.order.response.OrdersResponseDto;
import org.example.o2o.config.exception.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "주문 API", description = "주문 생성/상태 변경/취소")
public interface OrderDocsControllerV1 {

	@Operation(summary = "주문 생성", description = "주문을 생성합니다.")
	@ApiResponse(responseCode = "200", description = "주문 생성 뒤 storeId, orderId 반환")
	@ApiResponse(responseCode = "400", description = "잘못된 결제 수단",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "400", description = "잘못된 주문 정보",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	OrderCreateResponseDto registerOrder(
		@RequestBody OrderCreateRequestDto requestDto);

	@Operation(summary = "주문 상세 조회", description = "주문의 상세 정보를 조회")
	@ApiResponse(responseCode = "200", description = "orderId에 해당하는 주문 상세 정보 조회")
	@ApiResponse(responseCode = "404", description = "유효하지 않은 주문입니다.",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	OrderDetailResponseDto findById(@PathVariable Long orderId);

	@Operation(summary = "주문 목록 조회", description = "주문 목록을 조회")
	@ApiResponse(responseCode = "200", description = "memberId와 연관된 주문 목록 조회")
	@ApiResponse(responseCode = "400", description = "계정 정보가 올바르지 않습니다.",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	OrdersResponseDto findAll(Long memberId);
}
