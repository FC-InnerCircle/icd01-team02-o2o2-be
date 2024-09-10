package org.example.o2o.api.dto.order.request;

import java.util.List;

import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.order.OrderPaymentType;
import org.example.o2o.domain.order.OrderStatus;
import org.example.o2o.domain.store.Store;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record OrderCreateRequestDto(
	@Schema(description = "사용자 id", example = "1")
	@NotNull(message = "사용자 id는 필수입니다.")
	String memberId,

	@Schema(description = "가게 id", example = "1")
	@NotNull(message = "가게 id는 필수입니다.")
	Long storeId,

	@Schema(description = "가게명", example = "김밥천국")
	@NotNull(message = "가게명은 필수입니다.")
	String storeName,

	@Schema(description = "메뉴 목록", example = "[]")
	@NotNull(message = "메뉴 목록은 필수입니다.")
	@Length(min = 1, message = "메뉴는 하나 이상 선택되어야 합니다.")
	OrderMenuCreateRequestDto[] menus,

	@Schema(description = "주문 가격", example = "30000")
	@NotNull(message = "주문 가격은 필수입니다.")
	@Min(value = 0, message = "주문 가격은 최소 0원입니다.")
	Integer orderPrice,

	@Schema(description = "결제 수단", example = "card")
	@NotNull(message = "결제 수단은 필수입니다.")
	@Pattern(regexp = "^(card)?$", message = "결제 수단은 'card'만 가능합니다.")
	String payment,

	@Schema(description = "배달 주소 id", example = "1")
	@NotNull(message = "배달 주소 id는 필수입니다.")
	Long addressId) {

	public OrderInfo toOrder(Member member, Store store, Address address) {
		return OrderInfo.builder()
			.store(store)
			.member(member)
			.price(orderPrice)
			.menuDetail(List.of(menus))
			.address(address)
			.status(OrderStatus.PENDING)
			.payment(OrderPaymentType.toPaymentType(payment))
			.build();
	}
}
