package org.example.o2o.api.service.auth;

import java.util.Optional;

import org.example.o2o.api.dto.auth.AccountDto.FindProfileResponse;
import org.example.o2o.api.dto.auth.AccountDto.ModifyAccountResponse;
import org.example.o2o.api.dto.auth.AccountDto.ModifyProfileRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupAdminResponse;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.AccountErrorCode;
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

	/**
	 * 프로필 정보 조회
	 * @param id 계정 ID
	 * @return
	 */
	public FindProfileResponse findProfileInfo(Long id) {
		Account findAccount = accountRepository.findById(id)
			.orElseThrow(() -> new ApiException(AccountErrorCode.NOT_EXISTS_ACCOUNT_ID));

		return FindProfileResponse.of(findAccount);
	}

	/**
	 * 프로필 정보 변경
	 * @param id 계정 ID
	 * @param modifyRequest 변경할 프로필 정보
	 * @return 변경이 적용된 최종 계정 정보
	 */
	@Transactional
	public ModifyAccountResponse modifyProfile(Long id, ModifyProfileRequest modifyRequest) {
		Account findAccount = accountRepository.findById(id)
			.orElseThrow(() -> new ApiException(AccountErrorCode.NOT_EXISTS_ACCOUNT_ID));

		if (!findAccount.isActive()) {
			throw new ApiException(AccountErrorCode.INACTIVE_ACCOUNT_STATUS);
		}

		String encryptedPassword = passwordEncoder.encode(modifyRequest.getPassword());
		findAccount.updateProfileInfo(modifyRequest.toModifyProfile(encryptedPassword));

		return ModifyAccountResponse.of(findAccount);
	}

}
