package org.example.o2o.domain.order;

import java.util.Objects;

import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.order.OrderErrorCode;

import lombok.Getter;

@Getter
public enum OrderPaymentType {
	CARD("카드 결제");

	private final String text;

	OrderPaymentType(String text) {
		this.text = text;
	}

	public static OrderPaymentType toPaymentType(String text) {
		if (Objects.isNull(text) || text.isEmpty()) {
			throw new ApiException(OrderErrorCode.INVALID_PAYMENT_TYPE);
		}
		return valueOf(text.toUpperCase());
	}
}
