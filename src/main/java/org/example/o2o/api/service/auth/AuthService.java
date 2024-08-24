package org.example.o2o.api.service.auth;

import org.example.o2o.api.dto.auth.AuthDto.LoginRequest;
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
	 * 로그인 처리
	 * @param loginRequest 로그인 요청 정보
	 * @return
	 */
	public TokenResponse login(LoginRequest loginRequest) {

		Account findAccount = accountRepository.findByAccountId(loginRequest.getAccountId())
			.orElseThrow(() -> new ApiException(AccountErrorCode.INVALID_LOGIN_REQUEST));

		if (!passwordEncoder.matches(loginRequest.getPassword(), findAccount.getPassword())) {
			throw new ApiException(AccountErrorCode.INVALID_LOGIN_REQUEST);
		}

		String accessToken = tokenProvider.createAccessToken(TokenDto.AccessTokenClaimsInfo.of(findAccount));
		String refreshToken = tokenProvider.createRefreshToken(findAccount.getId());

		return new TokenResponse(accessToken, refreshToken);
	}

	/**
	 * 토큰 재발급
	 * @param reissueTokenRequest 토큰 재발급 요청 정보
	 * @return
	 */
	public TokenResponse reissueToken(ReissueTokenRequest reissueTokenRequest) {
		if (!tokenProvider.validateToken(reissueTokenRequest.getRefreshToken())) {
			throw new ApiException(TokenErrorCode.INVALID_TOKEN);
		}

		Long id = tokenProvider.extractPayload(reissueTokenRequest.getRefreshToken()).getId();
		Account findAccount = accountRepository.findById(id)
			.orElseThrow(() -> new ApiException(TokenErrorCode.INVALID_TOKEN));

		String accessToken = tokenProvider.createAccessToken(TokenDto.AccessTokenClaimsInfo.of(findAccount));
		String refreshToken = tokenProvider.createRefreshToken(findAccount.getId());

		return new TokenResponse(accessToken, refreshToken);
	}

}
