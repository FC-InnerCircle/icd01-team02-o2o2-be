package org.example.o2o.config.exception.enums.auth;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), String.valueOf(HttpStatus.UNAUTHORIZED.value()), "올바르지 않은 토큰입니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), String.valueOf(HttpStatus.UNAUTHORIZED.value()), "토큰이 만료되었습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
