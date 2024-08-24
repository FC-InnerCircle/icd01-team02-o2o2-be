package org.example.o2o.config.exception;

import org.example.o2o.config.exception.enums.ErrorCode;

public class ApiException extends RuntimeException {

	private final ErrorCode errorCode;

	public ApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ApiException(Throwable cause, ErrorCode errorCode) {
		super(cause);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
