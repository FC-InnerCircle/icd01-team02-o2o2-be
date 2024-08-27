package org.example.o2o.config.security.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class IpWhitelistFilter extends OncePerRequestFilter {

	private static final List<String> ALLOWED_IP_LIST = List.of("127.0.0.1"); // 허용할 IP 주소

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String remoteAddr = request.getRemoteAddr();

		if (ALLOWED_IP_LIST.contains(remoteAddr)) {
			// 허용된 IP의 경우 인증 처리
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				"IP_AUTHENTICATED_USER", null, Collections.singletonList(new SimpleGrantedAuthority("INTERNAL_CLIENT"))
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}
