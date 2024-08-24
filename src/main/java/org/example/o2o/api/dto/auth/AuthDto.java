package org.example.o2o.api.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

	@Getter
	@NoArgsConstructor
	public static class LoginRequest {
		private String accountId;
		private String password;
	}

	@Getter
	@NoArgsConstructor
	public static class ReissueTokenRequest {
		private String refreshToken;
	}
}
