package org.example.o2o.api.service.auth;

import org.example.o2o.api.dto.auth.AuthDto.LoginRequest;
import org.example.o2o.api.dto.auth.AuthDto.LogoutRequest;
import org.example.o2o.api.dto.auth.AuthDto.ReissueTokenRequest;
import org.example.o2o.common.component.TokenProvider;
import org.example.o2o.common.dto.jwt.TokenDto;
import org.example.o2o.common.dto.jwt.TokenDto.TokenResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.AccountErrorCode;
import org.example.o2o.config.exception.enums.auth.TokenErrorCode;
import org.example.o2o.domain.auth.Account;
import org.example.o2o.repository.auth.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	/**
	 * 로그인 처리<br/>
	 * 사용자가 입력한 계정 ID와 비밀번호를 확인하고, 유효한 경우 새로운 accessToken과 refreshToken을 발급합니다.<br/>
	 * 계정이 비활성화된 상태이거나 로그인 정보가 유효하지 않은 경우 로그인 요청이 거부됩니다.
	 * @param loginRequest 로그인 요청 정보
	 * @return 새로운 accessToken과 refreshToken을 포함한 TokenResponse 객체를 반환
	 * @throws ApiException 유효하지 않은 로그인 요청이거나 계정이 비활성화된 경우 예외 발생
	 */
	@Transactional
	public TokenResponse login(LoginRequest loginRequest) {

		Account findAccount = accountRepository.findByAccountId(loginRequest.getAccountId())
			.orElseThrow(() -> new ApiException(AccountErrorCode.INVALID_LOGIN_REQUEST));

		if (!findAccount.isActive()) {
			throw new ApiException(AccountErrorCode.INACTIVE_ACCOUNT_STATUS);
		}

		if (!passwordEncoder.matches(loginRequest.getPassword(), findAccount.getPassword())) {
			throw new ApiException(AccountErrorCode.INVALID_LOGIN_REQUEST);
		}

		String accessToken = tokenProvider.createAccessToken(TokenDto.AccessTokenClaimsInfo.of(findAccount));
		String refreshToken = tokenProvider.createRefreshToken(findAccount.getId());
		findAccount.updateRefreshToken(refreshToken);

		return new TokenResponse(accessToken, refreshToken);
	}

	/**
	 * 토큰 재발급<br/>
	 * 사용자가 요청한 refreshToken의 유효성을 검사하고, 유효한 경우 새로운 accessToken과 refreshToken을 발급합니다.
	 * @param reissueTokenRequest 토큰 재발급 요청 정보
	 * @return 새로운 accessToken과 refreshToken을 포함한 TokenResponse 객체를 반환
	 * @throws ApiException 유효하지 않은 토큰이 전달된 경우 또는 계정이 비활성화된 경우 예외 발생
	 */
	@Transactional
	public TokenResponse reissueToken(ReissueTokenRequest reissueTokenRequest) {
		if (!tokenProvider.validateToken(reissueTokenRequest.getRefreshToken())) {
			throw new ApiException(TokenErrorCode.INVALID_TOKEN);
		}

		Long id = tokenProvider.extractPayload(reissueTokenRequest.getRefreshToken()).getId();
		Account findAccount = accountRepository.findById(id)
			.orElseThrow(() -> new ApiException(TokenErrorCode.INVALID_TOKEN));

		if (!findAccount.isActive()) {
			throw new ApiException(AccountErrorCode.INACTIVE_ACCOUNT_STATUS);
		}

		String accessToken = tokenProvider.createAccessToken(TokenDto.AccessTokenClaimsInfo.of(findAccount));
		String refreshToken = tokenProvider.createRefreshToken(findAccount.getId());
		findAccount.updateRefreshToken(refreshToken);

		return new TokenResponse(accessToken, refreshToken);
	}

	/**
	 * 로그아웃 처리<br/>
	 * 주어진 refreshToken의 유효성을 검사하고, 유효한 경우 해당 사용자의 refreshToken을 폐기합니다.
	 * @param logoutRequest 로그아웃 요청 정보
	 * @throws ApiException 유효하지 않은 토큰이 전달된 경우 예외 발생
	 */
	@Transactional
	public void logout(LogoutRequest logoutRequest) {
		if (!tokenProvider.validateToken(logoutRequest.getRefreshToken())) {
			throw new ApiException(TokenErrorCode.INVALID_TOKEN);
		}

		Long id = tokenProvider.extractPayload(logoutRequest.getRefreshToken()).getId();
		Account findAccount = accountRepository.findById(id)
			.orElseThrow(() -> new ApiException(TokenErrorCode.INVALID_TOKEN));

		findAccount.clearRefreshToken();
	}
}
