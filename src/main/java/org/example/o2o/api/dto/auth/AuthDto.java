package org.example.o2o.api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginRequest {
		@NotBlank
		private String accountId;

		@NotBlank
		private String password;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ReissueTokenRequest {
		@NotBlank
		private String refreshToken;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LogoutRequest {
		@NotBlank
		private String refreshToken;
	}
}
