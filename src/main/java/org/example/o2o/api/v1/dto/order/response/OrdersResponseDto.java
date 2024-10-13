package org.example.o2o.api.v1.dto.order.response;

import java.util.Arrays;
import java.util.List;

import org.example.o2o.api.v1.dto.order.request.OrderMenuCreateRequestDto;
import org.example.o2o.api.v1.dto.order.request.OrderOptionCreateRequestDto;
import org.example.o2o.api.v1.dto.order.request.OrderOptionGroupCreateRequestDto;
import org.example.o2o.domain.order.OrderInfo;

import lombok.Builder;

@Builder
public record OrdersResponseDto(
	List<OrderResponse> orders
) {
	public static OrdersResponseDto of(List<OrderInfo> orders) {
		return OrdersResponseDto.builder().build();
	}

	@Builder
	public record OrderResponse(
		Long orderId,
		String orderTime,
		String orderStatus,
		Integer orderPrice,
		Long storeId,
		String storeName,
		List<MenuResponse> menus,
		Boolean isReviewed
	) {
		public static OrderResponse of(OrderInfo order) {
			return OrderResponse.builder()
				.orderId(order.getId())
				.orderTime(order.getCreatedAt().toString())
				.orderStatus(order.getStatus().getText())
				.orderPrice(order.getPrice())
				.storeId(order.getStore().getId())
				.storeName(order.getStore().getName())
				.menus(order.getMenuDetail()
					.stream()
					.map(MenuResponse::of)
					.toList())
				.isReviewed(false) // TODO review 작성 여부
				.build();
		}

		@Builder
		public record MenuResponse(
			Long menuId,
			String menuName,
			Integer menuCount,
			List<OptionGroupResponse> optionGroup
		) {

			public static MenuResponse of(OrderMenuCreateRequestDto menu) {
				return MenuResponse.builder()
					.menuId(menu.menuId())
					.menuName(menu.menuName())
					.menuCount(menu.menuCount())
					.optionGroup(Arrays.stream(menu.optionGroups())
						.map(OptionGroupResponse::of)
						.toList())
					.build();
			}

			@Builder
			public record OptionGroupResponse(
				Long optionGroupId,
				List<OptionResponse> option
			) {

				public static OptionGroupResponse of(OrderOptionGroupCreateRequestDto optionGroup) {
					return OptionGroupResponse.builder()
						.optionGroupId(optionGroup.optionGroupId())
						.option(Arrays.stream(optionGroup.options())
							.map(OptionResponse::of)
							.toList())
						.build();
				}

				@Builder
				public record OptionResponse(
					Long optionId,
					String optionName
				) {
					public static OptionResponse of(OrderOptionCreateRequestDto option) {
						return OptionResponse.builder()
							.optionId(option.optionId())
							.optionName(option.optionName())
							.build();
					}
				}
			}
		}
	}
}
