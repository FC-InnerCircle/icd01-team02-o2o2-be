package org.example.o2o.config.exception.enums.review;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

	DEUPLICATED_REVIEW(HttpStatus.CONFLICT.value(), String.valueOf(HttpStatus.CONFLICT.value()), "리뷰 정보가 이미 존재합니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
