package org.example.o2o.config.security.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.example.o2o.config.exception.ErrorResponse;
import org.example.o2o.config.exception.enums.common.CommonErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 미인증 사용자로 인증이 필요한 리소스에 접근한 경우
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		log.info("Unauthorized: {}", authException.getMessage());

		// 401 Unauthorized 응답을 클라이언트에게 전달
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(CommonErrorCode.UNAUTHORIZED)));
	}
}
