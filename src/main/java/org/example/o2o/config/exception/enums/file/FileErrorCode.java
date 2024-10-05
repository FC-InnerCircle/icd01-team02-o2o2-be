package org.example.o2o.config.exception.enums.file;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode {

	FILE_SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
		"파일 저장 중 에러가 발생하였습니다."),
	FILE_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR.value(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
		"파일 삭제 중 에러가 발생하였습니다."),
	NOT_FOUND(HttpStatus.BAD_REQUEST.value(), String.valueOf(HttpStatus.BAD_REQUEST.value()),
		"파일 정보를 찾을 수 없습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
