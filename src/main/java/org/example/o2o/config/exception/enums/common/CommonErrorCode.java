package org.example.o2o.config.exception.enums.common;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
		"알 수 없는 오류가 발생하였습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
