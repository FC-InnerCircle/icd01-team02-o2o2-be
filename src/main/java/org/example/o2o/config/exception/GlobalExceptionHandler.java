package org.example.o2o.config.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.example.o2o.config.exception.enums.ErrorCode;
import org.example.o2o.config.exception.enums.common.CommonErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 알 수 없는 예외 발생시(핸들링하지 못한 예외)
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleAllException(Exception ex, WebRequest request) {
		log.error("UNKNOWN_EXCEPTION_ERROR: ", ex);

		ErrorCode errorCode = CommonErrorCode.UNKNOWN_ERROR;

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(ErrorResponse.of(errorCode));
	}

	/**
	 * Spring Security 인증 실패 에러
	 *  - 미인증 에러, 권한 에러 모두 해당 예외 발생
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		log.error("ACCESS_DENIED_ERROR: ", ex);

		ErrorCode errorCode = CommonErrorCode.UNAUTHORIZED;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			errorCode = CommonErrorCode.ACCESS_DENIED;
		}

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(ErrorResponse.of(errorCode));
	}

	/**
	 * API 예외 발생시
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ErrorResponse> handleApiException(ApiException ex, WebRequest request) {
		log.error("API_EXCEPTION_ERROR: ", ex);

		ErrorCode errorCode = ex.getErrorCode();

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(ErrorResponse.of(errorCode));
	}

	/**
	 * Spring Validation 실패시
	 * @param ex
	 * @param request
	 * @return
	 */
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

	/**
	 * 요청 미디어 타입은 맞으나, 데이터 파싱에 실패한 경우
	 *  - application/json 요청이지만, Body가 없는 경우
	 *  - application/json 요청이지만, Body가 상이한 경우
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
		WebRequest request) {
		log.error("HTTP_MESSAGE_NOT_READABLE_EXCEPTION: ", ex);

		ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;

		Throwable cause = ex.getCause();
		if (cause instanceof InvalidFormatException) {
			InvalidFormatException ife = (InvalidFormatException)cause;

			if (ife.getTargetType().isEnum()) {
				String enumClassName = ife.getTargetType().getSimpleName();
				String invalidValue = ife.getValue().toString();
				String allowedValues = Arrays.stream(ife.getTargetType().getEnumConstants())
					.map(Object::toString)
					.collect(Collectors.joining(", "));

				String message = String.format("%s으로 '%s' 값을 사용할 수 없습니다. [%s]", enumClassName, invalidValue,
					allowedValues);

				return ResponseEntity
					.status(errorCode.getHttpStatusCode())
					.body(ErrorResponse.of(errorCode.getErrorCode(), message));
			}
		}

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(ErrorResponse.of(errorCode));
	}

	/**
	 * 요청 미디어 타입이 올바르지 않은 경우
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(
		HttpMediaTypeNotSupportedException ex, WebRequest request) {
		log.error("HTTP_MEDIA_TYPE_NOT_SUPPORTED_EXCEPTION: ", ex);

		ErrorCode errorCode = CommonErrorCode.UNSUPPORTED_MEDIA_TYPE;

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(ErrorResponse.of(errorCode));
	}

}
