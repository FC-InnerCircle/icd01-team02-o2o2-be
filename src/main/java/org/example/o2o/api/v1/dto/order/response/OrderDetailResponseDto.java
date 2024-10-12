package org.example.o2o.api.v1.dto.order.response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.example.o2o.api.v1.dto.order.request.OrderMenuCreateRequestDto;
import org.example.o2o.api.v1.dto.order.request.OrderOptionCreateRequestDto;
import org.example.o2o.api.v1.dto.order.request.OrderOptionGroupCreateRequestDto;
import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.order.OrderStatus;
import org.example.o2o.domain.store.Store;

import lombok.Builder;

@Builder
public record OrderDetailResponseDto(
	Long orderId,
	OrderStatus status,
	Integer price,
	LocalDateTime orderTime,
	StoreInfo store,
	Orderer orderer,
	List<Menu> menus
) {
	public static OrderDetailResponseDto of(OrderInfo order) {
		return OrderDetailResponseDto.builder()
			.orderId(order.getId())
			.status(order.getStatus())
			.price(order.getPrice())
			.orderTime(order.getCreatedAt())
			.store(StoreInfo.of(order.getStore()))
			.orderer(Orderer.of(order.getMember(), order.getAddress()))
			.menus(order.getMenuDetail()
				.stream()
				.map(Menu::of)
				.toList())
			.build();
	}

	@Builder
	public record Orderer(
		Long userId,
		String name,
		String phone,
		String address,
		String addressDetail,
		String zipCode
	) {
		public static Orderer of(Member member, Address address) {
			return Orderer.builder()
				.userId(member.getId())
				.name(member.getName())
				.phone(member.getContact())
				.address(address.getAddress())
				.addressDetail(address.getDetailAddress())
				.zipCode(address.getZipCode())
				.build();
		}
	}

	@Builder
	public record StoreInfo(
		Long storeId,
		String name
	) {
		public static StoreInfo of(Store store) {
			return StoreInfo.builder()
				.storeId(store.getId())
				.name(store.getName())
				.build();
		}
	}

	@Builder
	public record Menu(
		String name,
		Integer price,
		Integer quantity,
		List<OptionGroup> optionGroups

	) {
		public static Menu of(OrderMenuCreateRequestDto menu) {
			return Menu.builder()
				.name(menu.menuName())
				.price(menu.menuPrice())
				.quantity(menu.menuCount())
				.optionGroups(Arrays.stream(menu.optionGroups())
					.map(OptionGroup::of)
					.toList())
				.build();
		}

		@Builder
		public record OptionGroup(
			Long groupId,
			String name,
			List<Option> options
		) {
			public static OptionGroup of(OrderOptionGroupCreateRequestDto optionGroup) {
				return OptionGroup.builder()
					.groupId(optionGroup.optionGroupId())
					.name(optionGroup.optionName())
					.options(Arrays.stream(optionGroup.options())
						.map(Option::of)
						.toList())
					.build();
			}

			@Builder
			public record Option(
				Long optionId,
				String name,
				Integer price
			) {
				public static Option of(OrderOptionCreateRequestDto option) {
					return Option.builder()
						.optionId(option.optionId())
						.name(option.optionName())
						.price(option.optionPrice())
						.build();
				}
			}
		}
	}
}
