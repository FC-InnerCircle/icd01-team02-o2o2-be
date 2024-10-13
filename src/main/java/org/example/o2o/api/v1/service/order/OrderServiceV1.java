package org.example.o2o.api.v1.service.order;

import java.util.List;

import org.example.o2o.api.v1.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.v1.dto.order.response.OrderCreateResponseDto;
import org.example.o2o.api.v1.dto.order.response.OrderDetailResponseDto;
import org.example.o2o.api.v1.dto.order.response.OrdersResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.AccountErrorCode;
import org.example.o2o.config.exception.enums.auth.AddressErrorCode;
import org.example.o2o.config.exception.enums.order.OrderErrorCode;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.store.Store;
import org.example.o2o.repository.member.AddressRepository;
import org.example.o2o.repository.member.MemberRepository;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.order.OrderInfoRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceV1 {

	private final OrderInfoRepository orderRepository;
	private final MemberRepository memberRepository;
	private final StoreRepository storeRepository;
	private final StoreMenuRepository menuRepository;
	private final AddressRepository addressRepository;
	private final OrderNotificationService orderNotificationService;

	@Transactional
	public OrderCreateResponseDto order(final OrderCreateRequestDto requestDto) {

		Member member = memberRepository.findById(requestDto.memberId())
			.orElseThrow(() -> new ApiException(AccountErrorCode.INVALID_ACCOUNT_INFO));

		Store store = storeRepository.findById(requestDto.storeId())
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));

		Address address = addressRepository.findAddressWithMember(requestDto.addressId())
			.orElseThrow(() -> new ApiException(AddressErrorCode.NOTFOUND_ADDRESS));

		List<StoreMenu> menus = menuRepository.findStoreMenusWithDetails(requestDto.getMenuIds());

		if (!requestDto.validate(member, store, address, menus)) {
			throw new ApiException(OrderErrorCode.INVALID_ORDER);
		}

		return OrderCreateResponseDto.of(orderRepository.save(requestDto.toOrder(member, store, address)));
	}

	@Transactional(readOnly = true)
	public OrderDetailResponseDto getOrderDetail(final Long orderId) {

		if (!orderRepository.existsById(orderId)) {
			throw new ApiException(OrderErrorCode.NOT_EXISTS_ORDER);
		}

		return OrderDetailResponseDto.of(orderRepository.findByIdWithDetail(orderId));
	}

	@Transactional(readOnly = true)
	public OrdersResponseDto getOrdersByMemberId(final Long memberId) {

		if (!memberRepository.existsById(memberId)) {
			throw new ApiException(AccountErrorCode.INVALID_ACCOUNT_INFO);
		}

		return OrdersResponseDto.of(orderRepository.findByMemberId(memberId));
	}
}
