package org.example.o2o.api.dto.auth;

import org.example.o2o.domain.auth.Account;
import org.example.o2o.domain.auth.AccountRole;
import org.example.o2o.domain.auth.AccountStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountDto {

	@Getter
	@NoArgsConstructor
	public static class SignupOwnerRequest {
		@NotBlank
		private String accountId;

		@NotBlank
		private String password;

		@NotBlank
		private String name;

		@NotBlank
		private String contactNumber;

		@NotBlank
		private String email;

		public Account toAccount(String encryptedPassword) {
			return Account.builder()
				.accountId(accountId)
				.password(encryptedPassword)
				.name(name)
				.contactNumber(contactNumber)
				.email(email)
				.role(AccountRole.OWNER)
				.status(AccountStatus.ACTIVE)
				.build();
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignupOwnerResponse {
		private Long id;
		private String accountId;
		private String name;
		private String contactNumber;
		private String email;

		public static SignupOwnerResponse of(Account account) {
			return SignupOwnerResponse.builder()
				.id(account.getId())
				.accountId(account.getAccountId())
				.name(account.getName())
				.contactNumber(account.getContactNumber())
				.email(account.getEmail())
				.build();
		}
	}

	@Getter
	@NoArgsConstructor
	public static class SignupAdminRequest {
		@NotBlank
		private String accountId;

		@NotBlank
		private String password;

		@NotBlank
		private String name;

		@NotBlank
		private String contactNumber;

		@NotBlank
		private String email;

		public Account toAccount(String encryptedPassword) {
			return Account.builder()
				.accountId(accountId)
				.password(encryptedPassword)
				.name(name)
				.contactNumber(contactNumber)
				.email(email)
				.role(AccountRole.ADMIN)
				.status(AccountStatus.ACTIVE)
				.build();
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignupAdminResponse {
		private Long id;
		private String accountId;
		private String name;
		private String contactNumber;
		private String email;
		private AccountStatus status;

		public static SignupAdminResponse of(Account account) {
			return SignupAdminResponse.builder()
				.id(account.getId())
				.accountId(account.getAccountId())
				.name(account.getName())
				.contactNumber(account.getContactNumber())
				.email(account.getEmail())
				.status(account.getStatus())
				.build();
		}
	}
}
