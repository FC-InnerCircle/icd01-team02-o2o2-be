package org.example.o2o.api.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

	@Getter
	@NoArgsConstructor
	public static class LoginRequest {
		@NotBlank
		private String accountId;

		@NotBlank
		private String password;
	}

	@Getter
	@NoArgsConstructor
	public static class ReissueTokenRequest {
		@NotBlank
		private String refreshToken;
	}

	@Getter
	@NoArgsConstructor
	public static class LogoutRequest {
		@NotBlank
		private String refreshToken;
	}
}
