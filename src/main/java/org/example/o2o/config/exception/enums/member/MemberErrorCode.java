package org.example.o2o.config.exception.enums.member;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

	NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"회원 정보를 찾을 수 없습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
