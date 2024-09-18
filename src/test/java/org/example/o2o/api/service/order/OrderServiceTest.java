package org.example.o2o.api.service.order;

import static org.assertj.core.api.Assertions.*;

import org.example.o2o.api.dto.order.request.OrderCreateRequestDto;
import org.example.o2o.api.dto.order.request.OrderMenuCreateRequestDto;
import org.example.o2o.api.dto.order.request.OrderOptionCreateRequestDto;
import org.example.o2o.api.dto.order.request.OrderOptionGroupCreateRequestDto;
import org.example.o2o.api.dto.order.response.OrderCreateResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuOption;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.StoreFixture;
import org.example.o2o.fixture.menu.MenuFixture;
import org.example.o2o.repository.member.AddressRepository;
import org.example.o2o.repository.member.MemberRepository;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.order.OrderInfoRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

	@Autowired
	private OrderInfoRepository orderRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private StoreMenuRepository menuRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private StoreRepository storeRepository;

	@BeforeEach
	void setUp() {
		orderRepository.deleteAll();
	}

	@Test
	void testSaveOrderSuccessful() {
		Store store = storeRepository.save(StoreFixture.createCustomStore("가게 생성", ""));
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(store, 1));
		StoreMenuOptionGroup optionGroup = menu.getMenuOptionGroups().get(0);
		StoreMenuOption option = optionGroup.getOptions().get(0);
		Member member = memberRepository.save(Member.builder()
			.name("테스트 계정")
			.nickname("test")
			.contact("010-1234-5678")
			.loginStatus("")
			.status("")
			.build());
		Address address = addressRepository.save(Address.builder()
			.member(member)
			.address("주소")
			.detailAddress("123-1")
			.latitude(10.1F)
			.longitude(10.1F)
			.addressStatus("")
			.build());

		OrderOptionCreateRequestDto optionDto = new OrderOptionCreateRequestDto(option.getId(),
			option.getName(), option.getPrice());
		OrderOptionGroupCreateRequestDto optionGroupDto = new OrderOptionGroupCreateRequestDto(
			optionGroup.getId(), optionGroup.getTitle(),
			new OrderOptionCreateRequestDto[] {optionDto});
		OrderMenuCreateRequestDto menuDto = new OrderMenuCreateRequestDto(menu.getId(),
			menu.getName(), 2, menu.getPrice(),
			new OrderOptionGroupCreateRequestDto[] {optionGroupDto});
		OrderCreateRequestDto orderDto = OrderCreateRequestDto.builder()
			.memberId(member.getId())
			.storeId(store.getId())
			.storeName(store.getName())
			.menus(new OrderMenuCreateRequestDto[] {menuDto})
			.orderPrice(20000) // 메뉴 (10000 * 2) + 옵션 0 = 20000
			.payment("card")
			.addressId(address.getId())
			.build();

		OrderCreateResponseDto order = orderService.order(orderDto);
		assertThat(order.storeId()).isEqualTo(store.getId());

		OrderInfo saveOrder = orderRepository.findById(order.orderId()).orElse(null);
		assertThat(saveOrder).isNotNull();
		assertThat(saveOrder.getPrice()).isEqualTo(20000);
		OrderMenuCreateRequestDto saveMenuDto = (OrderMenuCreateRequestDto)saveOrder.getMenuDetail().get(0);
		assertThat(saveMenuDto).isEqualTo(menuDto);
	}

	@Test
	void testSaveOrderFail_orderPriceNotEquals() {
		Store store = storeRepository.save(StoreFixture.createCustomStore("가게 생성", ""));
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(store, 1));
		StoreMenuOptionGroup optionGroup = menu.getMenuOptionGroups().get(0);
		StoreMenuOption option = optionGroup.getOptions().get(0);
		Member member = memberRepository.save(Member.builder()
			.name("테스트 계정")
			.nickname("test")
			.contact("010-1234-5678")
			.loginStatus("")
			.status("")
			.build());
		Address address = addressRepository.save(Address.builder()
			.member(member)
			.address("주소")
			.detailAddress("123-1")
			.latitude(10.1F)
			.longitude(10.1F)
			.addressStatus("")
			.build());

		OrderOptionCreateRequestDto optionDto = new OrderOptionCreateRequestDto(option.getId(),
			option.getName(), option.getPrice());
		OrderOptionGroupCreateRequestDto optionGroupDto = new OrderOptionGroupCreateRequestDto(
			optionGroup.getId(), optionGroup.getTitle(),
			new OrderOptionCreateRequestDto[] {optionDto});
		OrderMenuCreateRequestDto menuDto = new OrderMenuCreateRequestDto(menu.getId(),
			menu.getName(), 2, menu.getPrice(),
			new OrderOptionGroupCreateRequestDto[] {optionGroupDto});
		OrderCreateRequestDto orderDto = OrderCreateRequestDto.builder()
			.memberId(member.getId())
			.storeId(store.getId())
			.storeName(store.getName())
			.menus(new OrderMenuCreateRequestDto[] {menuDto})
			.orderPrice(0)
			.payment("card")
			.addressId(address.getId())
			.build();

		assertThatThrownBy(() -> orderService.order(orderDto))
			.isInstanceOf(ApiException.class);
	}

	@Test
	void testSaveOrderFail_optionGroupNotMultiple() {
		Store store = storeRepository.save(StoreFixture.createCustomStore("가게 생성", ""));
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(store, 1));
		StoreMenuOptionGroup optionGroup = menu.getMenuOptionGroups().get(0);
		StoreMenuOption option1 = optionGroup.getOptions().get(0);
		StoreMenuOption option2 = optionGroup.getOptions().get(1);
		Member member = memberRepository.save(Member.builder()
			.name("테스트 계정")
			.nickname("test")
			.contact("010-1234-5678")
			.loginStatus("")
			.status("")
			.build());
		Address address = addressRepository.save(Address.builder()
			.member(member)
			.address("주소")
			.detailAddress("123-1")
			.latitude(10.1F)
			.longitude(10.1F)
			.addressStatus("")
			.build());

		OrderOptionCreateRequestDto optionDto1 = new OrderOptionCreateRequestDto(option1.getId(),
			option1.getName(), option1.getPrice());
		OrderOptionCreateRequestDto optionDto2 = new OrderOptionCreateRequestDto(option2.getId(),
			option2.getName(), option2.getPrice());
		OrderOptionGroupCreateRequestDto optionGroupDto = new OrderOptionGroupCreateRequestDto(
			optionGroup.getId(), optionGroup.getTitle(),
			new OrderOptionCreateRequestDto[] {optionDto1, optionDto2});
		OrderMenuCreateRequestDto menuDto = new OrderMenuCreateRequestDto(menu.getId(),
			menu.getName(), 2, menu.getPrice(),
			new OrderOptionGroupCreateRequestDto[] {optionGroupDto});
		OrderCreateRequestDto orderDto = OrderCreateRequestDto.builder()
			.memberId(member.getId())
			.storeId(store.getId())
			.storeName(store.getName())
			.menus(new OrderMenuCreateRequestDto[] {menuDto})
			.orderPrice(20000)
			.payment("card")
			.addressId(address.getId())
			.build();

		assertThatThrownBy(() -> orderService.order(orderDto))
			.isInstanceOf(ApiException.class);
	}

	@Test
	void testSaveOrderFail_idNotExists() {
		Store store = storeRepository.save(StoreFixture.createCustomStore("가게 생성", ""));
		Member member = memberRepository.save(Member.builder()
			.name("테스트 계정")
			.nickname("test")
			.contact("010-1234-5678")
			.loginStatus("")
			.status("")
			.build());

		OrderCreateRequestDto orderDtoInvalidMemberId = OrderCreateRequestDto.builder()
			.memberId(-99L)
			.build();

		assertThatThrownBy(() -> orderService.order(orderDtoInvalidMemberId))
			.isInstanceOf(ApiException.class);

		OrderCreateRequestDto orderDtoInvalidStoreId = OrderCreateRequestDto.builder()
			.memberId(member.getId())
			.storeId(-99L)
			.build();

		assertThatThrownBy(() -> orderService.order(orderDtoInvalidStoreId))
			.isInstanceOf(ApiException.class);

		OrderCreateRequestDto orderDtoInvalidAddressId = OrderCreateRequestDto.builder()
			.memberId(member.getId())
			.storeId(store.getId())
			.addressId(-99L)
			.build();

		assertThatThrownBy(() -> orderService.order(orderDtoInvalidAddressId))
			.isInstanceOf(ApiException.class);
	}
}
