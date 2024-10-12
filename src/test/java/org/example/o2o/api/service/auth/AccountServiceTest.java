package org.example.o2o.api.service.auth;

import static org.assertj.core.api.Assertions.*;

import org.example.o2o.api.v1.dto.auth.AccountDto.FindProfileResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.ModifyAccountResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.ModifyProfileRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupAdminResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupOwnerResponse;
import org.example.o2o.api.v1.service.auth.AccountService;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.AccountErrorCode;
import org.example.o2o.domain.auth.Account;
import org.example.o2o.domain.auth.AccountRole;
import org.example.o2o.domain.auth.AccountStatus;
import org.example.o2o.fixture.auth.AccountFixture;
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
public class AccountServiceTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@BeforeEach
	void setUp() {
		accountRepository.deleteAll();
	}

	@Nested
	@DisplayName("회원 가입 테스트")
	class SignupTest {

		@Test
		@DisplayName("어드민 회원가입 - 성공")
		void signupAdmin_success() {
			// Given
			SignupAdminRequest request = AccountFixture.createSignupAdminRequest("admin", "1234");

			// When
			SignupAdminResponse response = accountService.signupAdmin(request);

			// Then
			assertThat(response).isNotNull();
			assertThat(response.getAccountId()).isEqualTo(request.getAccountId());
			assertThat(response.getEmail()).isEqualTo(request.getEmail());
			assertThat(response.getStatus()).isEqualTo(AccountStatus.ACTIVE);
		}

		@Test
		@DisplayName("어드민 회원가입 - 실패: 아이디 중복")
		void signupAdmin_fail_1() {
			// Given
			accountRepository.save(AccountFixture.createAccount("admin", "1234", AccountRole.ADMIN));
			SignupAdminRequest request = AccountFixture.createSignupAdminRequest("admin", "1234");

			// When + Then
			assertThatThrownBy(() -> accountService.signupAdmin(request))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.DUPLICATED_ACCOUNT_ID);
		}

		@Test
		@DisplayName("점주 회원가입 - 성공")
		void signupOwner_success() {
			// Given
			SignupOwnerRequest request = AccountFixture.createSignupOwnerRequest("owner", "1234");

			// When
			SignupOwnerResponse response = accountService.signupOwner(request);

			// Then
			assertThat(response).isNotNull();
			assertThat(response.getAccountId()).isEqualTo(request.getAccountId());
			assertThat(response.getEmail()).isEqualTo(request.getEmail());
			assertThat(response.getStatus()).isEqualTo(AccountStatus.ACTIVE);
		}

		@Test
		@DisplayName("점주 회원가입 - 실패: 아이디 중복")
		void signupOwner_fail_1() {
			// Given
			accountRepository.save(AccountFixture.createAccount("owner", "1234", AccountRole.ADMIN));
			SignupAdminRequest request = AccountFixture.createSignupAdminRequest("owner", "1234");

			// When + Then
			assertThatThrownBy(() -> accountService.signupAdmin(request))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.DUPLICATED_ACCOUNT_ID);
		}
	}

	@Nested
	@DisplayName("프로필 정보 테스트")
	class ProfileTest {

		@Test
		@DisplayName("프로필 정보 조회 - 성공")
		void findProfileInfo_success() {
			// Given
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount("admin", "1234", AccountRole.ADMIN));

			// When
			FindProfileResponse profileInfo = accountService.findProfileInfo(savedAccount.getId());

			// Then
			assertThat(profileInfo).isNotNull();
			assertThat(profileInfo.getAccountId()).isEqualTo(savedAccount.getAccountId());
			assertThat(profileInfo.getEmail()).isEqualTo(savedAccount.getEmail());
			assertThat(profileInfo.getContactNumber()).isEqualTo(savedAccount.getContactNumber());
		}

		@Test
		@DisplayName("프로필 정보 조회 - 실패: 아이디가 존재하지 않음")
		void findProfileInfo_fail_1() {
			// Given

			// When + Then
			assertThatThrownBy(() -> accountService.findProfileInfo(1L))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.NOT_EXISTS_ACCOUNT_ID);
		}

		@Test
		@DisplayName("프로필 정보 변경 - 성공")
		void modifyProfile_success() {
			// Given
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount("admin", "1234", AccountRole.ADMIN));

			ModifyProfileRequest modifyRequest = ModifyProfileRequest.builder()
				.name("홍길동")
				.email("test@test.com")
				.contactNumber("01011112222")
				.password("1111")
				.build();

			// When
			ModifyAccountResponse modifyResponse = accountService.modifyProfile(savedAccount.getId(),
				modifyRequest);

			// Then
			assertThat(modifyResponse).isNotNull();
			assertThat(modifyResponse.getAccountId()).isEqualTo(savedAccount.getAccountId());
			assertThat(modifyResponse.getEmail()).isEqualTo(savedAccount.getEmail());
			assertThat(modifyResponse.getContactNumber()).isEqualTo(savedAccount.getContactNumber());
		}

		@Test
		@DisplayName("프로필 정보 변경 - 실패: 아이디가 존재하지 않음")
		void modifyProfile_fail_1() {
			// Given
			ModifyProfileRequest modifyRequest = ModifyProfileRequest.builder()
				.name("홍길동")
				.email("test@test.com")
				.contactNumber("01011112222")
				.password("1111")
				.build();

			// When + Then
			assertThatThrownBy(() -> accountService.modifyProfile(1L, modifyRequest))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.NOT_EXISTS_ACCOUNT_ID);
		}

		@Test
		@DisplayName("프로필 정보 변경 - 실패: 비활성화 계정")
		void modifyProfile_fail_2() {
			// Given
			Account savedAccount = accountRepository.save(
				AccountFixture.createAccount("admin", "1234", AccountRole.ADMIN));
			savedAccount.updateStatus(AccountStatus.LOCKED);
			accountRepository.save(savedAccount);

			ModifyProfileRequest modifyRequest = ModifyProfileRequest.builder()
				.name("홍길동")
				.email("test@test.com")
				.contactNumber("01011112222")
				.password("1111")
				.build();

			// When + Then
			assertThatThrownBy(() -> accountService.modifyProfile(savedAccount.getId(), modifyRequest))
				.isInstanceOf(ApiException.class)
				.extracting("errorCode")
				.isEqualTo(AccountErrorCode.INACTIVE_ACCOUNT_STATUS);
		}
	}

}
