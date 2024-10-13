package org.example.o2o.api.v2.service.order;

import java.time.LocalDateTime;
import java.util.List;

import org.example.o2o.api.v2.dto.order.request.OrdersRequestDto;
import org.example.o2o.api.v2.dto.order.response.OrderDetailResponseDto;
import org.example.o2o.api.v2.dto.order.response.OrdersResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.order.OrderErrorCode;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.order.OrderStatus;
import org.example.o2o.repository.order.OrderInfoRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceV2 {

	private final OrderInfoRepository orderRepository;
	private final StoreRepository storeRepository;

	@Transactional(readOnly = true)
	public OrdersResponseDto getOrdersByStoreId(
		final Long storeId,
		final OrdersRequestDto requestDto) {

		if (!storeRepository.existsById(storeId)) {
			throw new ApiException(StoreErrorCode.NOT_EXISTS_STORE);
		}

		List<OrderStatus> statues = OrderStatus.getOrderStatuses(requestDto.status());
		return OrdersResponseDto.of(orderRepository.findByStoreIdAndStatusIn(
			storeId, statues, requestDto.toPageRequest(),
			LocalDateTime.parse(requestDto.startDate() + "T00:00:00"),
			LocalDateTime.parse(requestDto.endDate() + "T23:59:59")));
	}

	@Transactional(readOnly = true)
	public OrderDetailResponseDto getOrderDetail(final Long orderId) {

		if (!orderRepository.existsById(orderId)) {
			throw new ApiException(OrderErrorCode.NOT_EXISTS_ORDER);
		}

		return OrderDetailResponseDto.of(orderRepository.findByIdWithDetail(orderId));
	}
}
