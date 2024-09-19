package org.example.o2o.config.exception.enums.member;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressErrorCode implements ErrorCode {

	NOT_FOUND_ADDRESS(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"회원의 주소 정보를 찾을 수 없습니다."),
	INVALID_MEMBER_ID(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"회원 정보가 올바르지 않습니다."),
	NOT_ACTIVE_STATUS(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"주소 정보가 활성화 상태가 아닙니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
