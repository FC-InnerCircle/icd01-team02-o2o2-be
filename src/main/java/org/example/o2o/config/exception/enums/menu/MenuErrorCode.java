package org.example.o2o.config.exception.enums.menu;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuErrorCode implements ErrorCode {

	NOTFOUND_MENU(HttpStatus.NOT_FOUND.value(), String.valueOf(HttpStatus.NOT_FOUND.value()), "메뉴를 조회할 수 없습니다."),
	NOTFOUND_MENU_OPTION(HttpStatus.NOT_FOUND.value(), String.valueOf(HttpStatus.NOT_FOUND.value()),
		"메뉴 옵션을 조회할 수 없습니다.");

	private final int httpStatusCode;
	private final String errorCode;
	private final String errorMessage;
}
