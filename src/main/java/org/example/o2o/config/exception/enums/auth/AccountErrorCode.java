package org.example.o2o.config.exception.enums.auth;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {

	DUPLICATED_ACCOUNT_ID(HttpStatus.CONFLICT.value(), String.valueOf(HttpStatus.CONFLICT.value()), "아이디가 존재합니다."),
	INVALID_LOGIN_REQUEST(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요."),
	NOT_EXISTS_ACCOUNT_ID(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"아이디가 존재하지 않습니다."),
	INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"이메일 형식이 올바르지 않습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
