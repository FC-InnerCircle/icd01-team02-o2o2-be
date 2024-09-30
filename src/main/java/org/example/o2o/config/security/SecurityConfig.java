package org.example.o2o.config.security;

import java.util.List;

import org.example.o2o.common.component.TokenProvider;
import org.example.o2o.config.security.filter.JwtAuthenticationFilter;
import org.example.o2o.config.security.handler.CustomAccessDeniedHandler;
import org.example.o2o.config.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final TokenProvider tokenProvider;
	private final CustomUserDetailsService userDetailsService;
	private final ObjectMapper objectMapper;

	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.cors(httpSecurityCorsConfigurer ->
				httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource())
			)
			.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests
					.requestMatchers("/health/check").permitAll()
					.requestMatchers("/api/v1/auth/*").permitAll()
					.requestMatchers("/api/v1/accounts/*").hasRole("ADMIN")
					.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
					.anyRequest().permitAll()
			)
			.logout(logout ->
				logout.logoutSuccessUrl("/api/v1/auth/logout")
					.invalidateHttpSession(true)
			)
			.sessionManagement(httpSecuritySessionManagementConfigurer ->
				httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.exceptionHandling(exceptionHandling ->
				exceptionHandling.accessDeniedHandler(customAccessDeniedHandler)
					.authenticationEntryPoint(customAuthenticationEntryPoint)
			)
			//.addFilterBefore(new IpWhitelistFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService, objectMapper),
				UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(
			List.of("http://localhost:3000", "http://localhost:8080", "http://o2o-admin.com:8080",
				"https://o2o-admin.com"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
