package org.example.o2o.config.exception.enums.order;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements ErrorCode {

	INVALID_PAYMENT_TYPE(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"잘못된 결제 수단입니다."),
	INVALID_ORDER(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()), "주문 정보가 올바르지 않습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
