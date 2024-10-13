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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record OrderDetailResponseDto(
	@Schema(description = "주문 id", example = "1")
	Long orderId,

	@Schema(description = "주문 시간", example = "2021-11-08T11:44:30")
	LocalDateTime orderTime,

	@Schema(description = "주문 상태", example = "PENDING | ACCEPTED | PREPARING | DELIVERING | DELIVERED | CANCELED")
	OrderStatus orderStatus,

	@Schema(description = "주문 총 가격", example = "14000")
	Integer orderPrice,

	@Schema(description = "가게 정보", example = "{}")
	StoreInfo store,

	@Schema(description = "메뉴 정보", example = "[]")
	List<Menu> menus,

	@Schema(description = "주문자 정보", example = "{}")
	Orderer orderer
) {
	public static OrderDetailResponseDto of(OrderInfo order) {
		return OrderDetailResponseDto.builder()
			.orderId(order.getId())
			.orderStatus(order.getStatus())
			.orderPrice(order.getPrice())
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
		@Schema(description = "주문자 id", example = "1")
		Long userId,

		@Schema(description = "주문자 이름", example = "사용자명")
		String name,

		@Schema(description = "휴대폰 번호", example = "01011112222")
		String phone,

		@Schema(description = "배달 주소", example = "서울 특별시 마포구 마장로 111-21")
		String address,

		@Schema(description = "배달 상세 주소", example = "11층 1101호")
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
		@Schema(description = "가게 id", example = "1")
		Long storeId,
		@Schema(description = "가게명", example = "동대문 엽기 떡볶이")
		String storeName,
		@Schema(description = "가게 주소", example = "{}")
		StoreAddress storeAddress
	) {
		public static StoreInfo of(Store store) {
			return StoreInfo.builder()
				.storeId(store.getId())
				.storeName(store.getName())
				.build();
		}

		@Builder
		public record StoreAddress(
			@Schema(description = "위도", example = "37.5665")
			String latitude,
			@Schema(description = "경도", example = "126.9780")
			String longitude,
			@Schema(description = "가게 주소", example = "서울 특별시 마포구")
			String address,
			@Schema(description = "가게 상세 주소", example = "11층 1102호")
			String addressDetail,
			@Schema(description = "우편 번호", example = "111-11")
			String zipCode
		) {
			public static StoreAddress of(Address address) {
				return StoreAddress.builder()
					.latitude(String.valueOf(address.getLatitude()))
					.longitude(String.valueOf(address.getLongitude()))
					.address(address.getAddress())
					.addressDetail(address.getDetailAddress())
					.zipCode(address.getZipCode())
					.build();
			}
		}
	}

	@Builder
	public record Menu(
		@Schema(description = "메뉴 id", example = "1")
		Long menuId,

		@Schema(description = "메뉴명", example = "떡볶이")
		String menuName,

		@Schema(description = "메뉴 수량", example = "2")
		Integer menuCount,

		@Schema(description = "메뉴 옵션 그룹", example = "{}")
		List<OptionGroup> optionGroups

	) {
		public static Menu of(OrderMenuCreateRequestDto menu) {
			return Menu.builder()
				.menuId(menu.menuId())
				.menuName(menu.menuName())
				.menuCount(menu.menuCount())
				.optionGroups(Arrays.stream(menu.optionGroups())
					.map(OptionGroup::of)
					.toList())
				.build();
		}

		@Builder
		public record OptionGroup(
			@Schema(description = "옵션 그룹 id", example = "1")
			Long optionGroupId,

			@Schema(description = "옵션 그룹명", example = "사리 추가")
			String optionGroupName,

			@Schema(description = "옵션 목록", example = "[]")
			List<Option> options
		) {
			public static OptionGroup of(OrderOptionGroupCreateRequestDto optionGroup) {
				return OptionGroup.builder()
					.optionGroupId(optionGroup.optionGroupId())
					.optionGroupName(optionGroup.optionName())
					.options(Arrays.stream(optionGroup.options())
						.map(Option::of)
						.toList())
					.build();
			}

			@Builder
			public record Option(
				@Schema(description = "옵션 id", example = "1")
				Long optionId,

				@Schema(description = "옵션명", example = "햄 추가")
				String optionName
			) {
				public static Option of(OrderOptionCreateRequestDto option) {
					return Option.builder()
						.optionId(option.optionId())
						.optionName(option.optionName())
						.build();
				}
			}
		}
	}
}
