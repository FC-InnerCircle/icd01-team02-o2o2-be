package org.example.o2o.config.exception;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.example.o2o.config.exception.enums.common.CommonErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleAllException(Exception ex, WebRequest request) {
		log.error("UNKNOWN_EXCEPTION_ERROR: ", ex);

		ErrorCode errorCode = CommonErrorCode.UNKNOWN_ERROR;

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(ErrorResponse.of(errorCode));
	}

	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ErrorResponse> handleApiException(ApiException ex, WebRequest request) {
		ErrorCode errorCode = ex.getErrorCode();

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(ErrorResponse.of(errorCode));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
		WebRequest request) {
		log.error("METHOD_ARGUMENT_NOT_VALID_ERROR: ", ex);
		FieldError fieldError = ex.getBindingResult().getFieldError();
		String errorMessage = fieldError.getDefaultMessage();

		return ResponseEntity
			.status(ex.getStatusCode())
			.body(ErrorResponse.of(ex.getStatusCode(), errorMessage));
	}

}
