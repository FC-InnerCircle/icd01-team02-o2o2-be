package org.example.o2o.config.exception;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.springframework.http.HttpStatusCode;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

	private String code;
	private String message;

	@Builder
	public ErrorResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ErrorResponse of(HttpStatusCode statusCode, String message) {
		return ErrorResponse.builder()
			.code(String.valueOf(statusCode.value()))
			.message(message)
			.build();
	}

	public static ErrorResponse of(String code, String message) {
		return ErrorResponse.builder()
			.code(code)
			.message(message)
			.build();
	}

	public static ErrorResponse of(ErrorCode errorCode) {
		return ErrorResponse.builder()
			.code(errorCode.getErrorCode())
			.message(errorCode.getErrorMessage())
			.build();
	}

}
