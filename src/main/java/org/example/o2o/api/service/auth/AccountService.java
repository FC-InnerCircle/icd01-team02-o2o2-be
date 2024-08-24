package org.example.o2o.api.service.auth;

import java.util.Optional;

import org.example.o2o.api.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupAdminResponse;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.AccountErrorCode;
import org.example.o2o.domain.auth.Account;
import org.example.o2o.repository.auth.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 점주 회원 가입
	 * @param signupRequest
	 * @return
	 */
	public SignupOwnerResponse signupOwner(SignupOwnerRequest signupRequest) {
		// 아이디 중복 체크
		Optional<Account> accountInfo = accountRepository.findByAccountId(signupRequest.getAccountId());
		if (accountInfo.isPresent()) {
			throw new ApiException(AccountErrorCode.DUPLICATED_ACCOUNT_ID);
		}

		// 계정 정보 등록
		String encryptedPassword = passwordEncoder.encode(signupRequest.getPassword());
		Account savedAccount = accountRepository.save(signupRequest.toAccount(encryptedPassword));

		return SignupOwnerResponse.of(savedAccount);
	}

	/**
	 * 관리자 회원가입
	 * @param signupRequest
	 * @return
	 */
	public SignupAdminResponse signupAdmin(SignupAdminRequest signupRequest) {
		// 아이디 중복 체크
		Optional<Account> accountInfo = accountRepository.findByAccountId(signupRequest.getAccountId());
		if (accountInfo.isPresent()) {
			throw new ApiException(AccountErrorCode.DUPLICATED_ACCOUNT_ID);
		}

		// 계정 정보 등록
		String encryptedPassword = passwordEncoder.encode(signupRequest.getPassword());
		Account savedAccount = accountRepository.save(signupRequest.toAccount(encryptedPassword));

		return SignupAdminResponse.of(savedAccount);
	}

}
