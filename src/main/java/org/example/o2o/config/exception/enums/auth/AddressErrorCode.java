package org.example.o2o.config.exception.enums.auth;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressErrorCode implements ErrorCode {
	NOTFOUND_ADDRESS(HttpStatus.CONFLICT.value(), String.valueOf(HttpStatus.CONFLICT.value()), "아이디가 존재합니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
