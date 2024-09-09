package org.example.o2o.domain.order;

import lombok.Getter;

@Getter
public enum OrderPaymentType {
	CARD("카드 결제");

	private final String text;

	OrderPaymentType(String text) {
		this.text = text;
	}
}
