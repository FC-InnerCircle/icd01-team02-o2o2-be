package org.example.o2o.api.service.order;

import org.example.o2o.api.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.dto.order.response.OrderCreateResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

	@Transactional
	public OrderCreateResponseDto order(final OrderCreateRequestDto requestDto) {
		return null;
	}
}
