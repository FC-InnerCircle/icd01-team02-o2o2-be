package org.example.o2o.config.exception.enums.common;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

	UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
		"알 수 없는 오류가 발생하였습니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"요청 데이터 형식이 올바르지 않습니다."),
	UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
		String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()),
		"요청 미디어 포맷은 서버에서 지원하지 않습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(),
		String.valueOf(HttpStatus.UNAUTHORIZED.value()),
		"인증이 필요합니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN.value(),
		String.valueOf(HttpStatus.FORBIDDEN.value()),
		"권한이 올바르지 않습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
