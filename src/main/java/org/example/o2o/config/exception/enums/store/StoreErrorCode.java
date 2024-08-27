package org.example.o2o.config.exception.enums.store;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {
	NOT_EXISTS_STORE(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"음식점을 찾을 수 없습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
