package org.example.o2o.config.security.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.example.o2o.config.exception.ErrorResponse;
import org.example.o2o.config.exception.enums.common.CommonErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 인증된 사용자이지만 권한이 올바르지 않은 경우
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException)
		throws IOException, ServletException {

		log.info("Access denied: {}", accessDeniedException.getMessage());

		// 403 Forbidden 응답을 클라이언트에게 전달
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(CommonErrorCode.ACCESS_DENIED)));
	}
}