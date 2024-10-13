package org.example.o2o.api.service.auth;

import static org.assertj.core.api.Assertions.*;

import org.example.o2o.api.v1.dto.auth.AuthDto.LoginRequest;
import org.example.o2o.api.v1.dto.auth.AuthDto.LogoutRequest;
import org.example.o2o.api.v1.dto.auth.AuthDto.ReissueTokenRequest;
import org.example.o2o.api.v1.service.auth.AuthServiceV1;
import org.example.o2o.common.component.TokenProvider;
import org.example.o2o.common.dto.jwt.TokenDto.TokenResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.AccountErrorCode;
import org.example.o2o.config.exception.enums.auth.TokenErrorCode;
import org.example.o2o.domain.auth.Account;
import org.example.o2o.domain.auth.AccountRole;
import org.example.o2o.domain.auth.AccountStatus;
import org.example.o2o.fixture.auth.AccountFixture;
import org.example.o2o.fixture.auth.AuthFixture;
import org.example.o2o.repository.auth.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AuthServiceV1Test {

	@Autowired
	private AuthServiceV1 authService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TokenProvider tokenProvider;

	@BeforeEach
	void setUp() {
		accountRepository.deleteAll();
	}

	@Nested
	@DisplayName("로그인 테스트")
	class LoginTest {

		@Test
		@DisplayName("로그인 - 성공")
		void login_success() {
			// Given
			String accountId = "admin";
			String password = "1234";
			accountRepository.save(
				AccountFixture.createAccount(accountId, password, AccountRole.ADMIN));

			LoginRequest loginRequest = AuthFixture.createLoginRequest(accountId, password);

			// When
			TokenResponse loginResponse = authService.login(loginRequest);

			// Then
			boolean isAccessTokenValid = tokenProvider.validateToken(loginResponse.getAccessToken());
			boolean isRefreshTokenValid = tokenProvider.validateToken(loginResponse.getRefreshToken());

			assertThat(loginResponse).isNotNull();
			assertThat(isAccessTokenValid).isTrue();
			assertThat(isRefreshTokenValid).isTrue();
		}

		@Test
		@DisplayName("로그인 - 실패: 비활성화 계정")
		void login_fail_1() {
			// Given
			String accountId = "admin";
			String password = "1234";
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount(accountId, password, AccountRole.ADMIN));
			savedAccount.updateStatus(AccountStatus.LOCKED);
			accountRepository.save(savedAccount);

			LoginRequest loginRequest = AuthFixture.createLoginRequest(accountId, password);

			// When + Then
			assertThatThrownBy(() -> authService.login(loginRequest))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.INACTIVE_ACCOUNT_STATUS);
		}

		@Test
		@DisplayName("로그인 - 실패: 비밀번호가 올바르지 않음")
		void login_fail_2() {
			// Given
			String accountId = "admin";
			String password = "1234";
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount(accountId, password, AccountRole.ADMIN));

			LoginRequest loginRequest = AuthFixture.createLoginRequest(accountId, password + "password");

			// When + Then
			assertThatThrownBy(() -> authService.login(loginRequest))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.INVALID_LOGIN_REQUEST);
		}
	}

	@Nested
	@DisplayName("토큰 재발급 테스트")
	class ReissueTokenTest {

		@Test
		@DisplayName("토큰 재발급 - 성공")
		void reissueToken_success() {
			// Given
			String accountId = "admin";
			String password = "1234";
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount(accountId, password, AccountRole.ADMIN));

			LoginRequest loginRequest = AuthFixture.createLoginRequest(accountId, password);
			TokenResponse loginResponse = authService.login(loginRequest);

			String refreshToken = loginResponse.getRefreshToken();
			ReissueTokenRequest reissueTokenRequest = AuthFixture.createReissueTokenRequest(refreshToken);

			// When
			TokenResponse tokenResponse = authService.reissueToken(reissueTokenRequest);

			// Then
			boolean isAccessTokenValid = tokenProvider.validateToken(tokenResponse.getAccessToken());
			boolean isRefreshTokenValid = tokenProvider.validateToken(tokenResponse.getRefreshToken());

			assertThat(tokenResponse).isNotNull();
			assertThat(isAccessTokenValid).isTrue();
			assertThat(isRefreshTokenValid).isTrue();
		}

		@Test
		@DisplayName("토큰 재발급 - 토큰 검증 실패")
		void reissueToken_fail_1() {
			// Given
			String accountId = "admin";
			String password = "1234";
			accountRepository.save(
				AccountFixture.createAccount(accountId, password, AccountRole.ADMIN));

			LoginRequest loginRequest = AuthFixture.createLoginRequest(accountId, password);
			TokenResponse loginResponse = authService.login(loginRequest);

			String refreshToken = loginResponse.getRefreshToken();
			ReissueTokenRequest reissueTokenRequest = AuthFixture.createReissueTokenRequest(refreshToken + "noise");

			// When + Then
			assertThatThrownBy(() -> authService.reissueToken(reissueTokenRequest))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(TokenErrorCode.INVALID_TOKEN);
		}

		@Test
		@DisplayName("토큰 재발급 - 비활성화 계정")
		void reissueToken_fail_2() {
			// Given
			String accountId = "admin";
			String password = "1234";
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount(accountId, password, AccountRole.ADMIN));

			LoginRequest loginRequest = AuthFixture.createLoginRequest(accountId, password);
			TokenResponse loginResponse = authService.login(loginRequest);

			savedAccount.updateStatus(AccountStatus.LOCKED);
			accountRepository.save(savedAccount);

			String refreshToken = loginResponse.getRefreshToken();
			ReissueTokenRequest reissueTokenRequest = AuthFixture.createReissueTokenRequest(refreshToken);

			// When + Then
			assertThatThrownBy(() -> authService.reissueToken(reissueTokenRequest))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.INACTIVE_ACCOUNT_STATUS);

		}
	}

	@Nested
	@DisplayName("로그아웃 테스트")
	class LogoutTest {

		@Test
		@DisplayName("로그아웃 - 성공")
		void login_success() {
			// Given
			String accountId = "admin";
			String password = "1234";
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount(accountId, password, AccountRole.ADMIN));

			LoginRequest loginRequest = AuthFixture.createLoginRequest(accountId, password);
			TokenResponse loginResponse = authService.login(loginRequest);

			String refreshToken = loginResponse.getRefreshToken();
			LogoutRequest logoutRequest = AuthFixture.createLogoutRequest(refreshToken);

			// When
			authService.logout(logoutRequest);

			// Then
			Account account = accountRepository.findById(savedAccount.getId())
				.orElseThrow(() -> new IllegalArgumentException("테스트 에러"));

			assertThat(account.getRefreshToken()).isNull();
		}
	}

}
