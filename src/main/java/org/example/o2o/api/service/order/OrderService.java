package org.example.o2o.api.service.order;

import java.util.List;

import org.example.o2o.api.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.dto.order.response.OrderCreateResponseDto;
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
public class OrderService {

	private final OrderInfoRepository orderRepository;
	private final MemberRepository memberRepository;
	private final StoreRepository storeRepository;
	private final StoreMenuRepository menuRepository;
	private final AddressRepository addressRepository;

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
}
