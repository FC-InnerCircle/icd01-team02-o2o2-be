package org.example.o2o.fixture.auth;

import org.example.o2o.api.dto.auth.AuthDto.LoginRequest;
import org.example.o2o.api.dto.auth.AuthDto.LogoutRequest;
import org.example.o2o.api.dto.auth.AuthDto.ReissueTokenRequest;

public class AuthFixture {

	public static LoginRequest createLoginRequest(String accountId, String password) {
		return LoginRequest.builder()
			.accountId(accountId)
			.password(password)
			.build();
	}

	public static ReissueTokenRequest createReissueTokenRequest(String reissueToken) {
		return ReissueTokenRequest.builder()
			.refreshToken(reissueToken)
			.build();
	}

	public static LogoutRequest createLogoutRequest(String reissueToken) {
		return LogoutRequest.builder()
			.refreshToken(reissueToken)
			.build();
	}
}
