package org.example.o2o.config.security.filter;

import java.io.IOException;

import org.example.o2o.common.component.TokenProvider;
import org.example.o2o.common.dto.jwt.Payload;
import org.example.o2o.config.security.CustomUserDetails;
import org.example.o2o.domain.auth.Account;
import org.example.o2o.domain.auth.AccountRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	private final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// Authorization 헤더 검증
		String authorization = request.getHeader("Authorization");
		if (authorization == null || !authorization.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 토큰 유효성 검증
		String token = authorization.substring(AUTHORIZATION_HEADER_PREFIX.length());
		if (!tokenProvider.validateToken(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 페이로드 추출 후 계정 정보 생성
		Payload payload = tokenProvider.extractPayload(token);
		Account account = Account.builder()
			.accountId(payload.getAccountId())
			.role(AccountRole.valueOf(payload.getRole()))
			.name(payload.getName())
			.build();

		// 인증 정보 생성 및 설정
		CustomUserDetails customerUserDetails = new CustomUserDetails(account);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
			new UsernamePasswordAuthenticationToken(
				customerUserDetails, null, customerUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

		filterChain.doFilter(request, response);
	}
}
