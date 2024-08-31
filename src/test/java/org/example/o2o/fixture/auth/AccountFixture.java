package org.example.o2o.fixture.auth;

import org.example.o2o.api.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.domain.auth.Account;
import org.example.o2o.domain.auth.AccountRole;
import org.example.o2o.domain.auth.AccountStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AccountFixture {

	public static Account createAccount(String accountId, String password, AccountRole role) {
		return Account.builder()
			.name(accountId)
			.accountId(accountId)
			.password(new BCryptPasswordEncoder().encode(password))
			.email(accountId + "@test.com")
			.contactNumber("01011112222")
			.role(role)
			.status(AccountStatus.ACTIVE)
			.build();
	}

	public static SignupOwnerRequest createSignupOwnerRequest(String accountId, String password) {
		return SignupOwnerRequest.builder()
			.name(accountId)
			.accountId(accountId)
			.password(new BCryptPasswordEncoder().encode(password))
			.email(accountId + "@test.com")
			.contactNumber("01011112222")
			.build();
	}

	public static SignupAdminRequest createSignupAdminRequest(String accountId, String password) {
		return SignupAdminRequest.builder()
			.name(accountId)
			.accountId(accountId)
			.password(new BCryptPasswordEncoder().encode(password))
			.email(accountId + "@test.com")
			.contactNumber("01011112222")
			.build();
	}
}
