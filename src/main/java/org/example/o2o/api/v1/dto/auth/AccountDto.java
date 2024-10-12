package org.example.o2o.api.v1.dto.auth;

import org.example.o2o.domain.auth.Account;
import org.example.o2o.domain.auth.AccountCommand.ModifyProfile;
import org.example.o2o.domain.auth.AccountRole;
import org.example.o2o.domain.auth.AccountStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountDto {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignupOwnerRequest {

		@Schema(description = "아이디", example = "test")
		@NotBlank(message = "아이디는 필수 입력입니다.")
		private String accountId;

		@Schema(description = "비밀번호", example = "1234")
		@NotBlank(message = "비밀번호는 필수 입력입니다.")
		private String password;

		@Schema(description = "이름", example = "홍길동")
		@NotBlank(message = "이름은 필수 입력입니다.")
		private String name;

		@Schema(description = "연락처(숫자만 입력)", example = "01012345678")
		@NotBlank(message = "연락처는 필수 입력입니다.")
		private String contactNumber;

		@Schema(description = "이메일", example = "test@test.com")
		@NotBlank(message = "이메일은 필수 입력입니다.")
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
		@Schema(description = "계정 ID", example = "1")
		private Long id;

		@Schema(description = "아이디", example = "test")
		private String accountId;

		@Schema(description = "이름", example = "홍길동")
		private String name;

		@Schema(description = "연락처", example = "01012345678")
		private String contactNumber;

		@Schema(description = "이메일", example = "test@test.com")
		private String email;

		@Schema(description = "상태", example = "ACTIVE")
		private AccountStatus status;

		public static SignupOwnerResponse of(Account account) {
			return SignupOwnerResponse.builder()
				.id(account.getId())
				.accountId(account.getAccountId())
				.name(account.getName())
				.contactNumber(account.getContactNumber())
				.email(account.getEmail())
				.status(account.getStatus())
				.build();
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SignupAdminRequest {
		@Schema(description = "아이디", example = "test")
		@NotBlank(message = "아이디는 필수 입력입니다.")
		private String accountId;

		@Schema(description = "비밀번호", example = "1234")
		@NotBlank(message = "비밀번호는 필수 입력입니다.")
		private String password;

		@Schema(description = "이름", example = "홍길동")
		@NotBlank(message = "이름은 필수 입력입니다.")
		private String name;

		@Schema(description = "연락처(숫자만 입력)", example = "01012345678")
		@NotBlank(message = "연락처는 필수 입력입니다.")
		private String contactNumber;

		@Schema(description = "이메일", example = "test@test.com")
		@NotBlank(message = "이메일은 필수 입력입니다.")
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
		@Schema(description = "계정 ID", example = "1")
		private Long id;

		@Schema(description = "아이디", example = "test")
		private String accountId;

		@Schema(description = "이름", example = "홍길동")
		private String name;

		@Schema(description = "연락처", example = "01012345678")
		private String contactNumber;

		@Schema(description = "이메일", example = "test@test.com")
		private String email;

		@Schema(description = "상태", example = "ACTIVE")
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

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FindProfileResponse {
		@Schema(description = "계정 ID", example = "1")
		private Long id;

		@Schema(description = "아이디", example = "test")
		private String accountId;

		@Schema(description = "이름", example = "홍길동")
		private String name;

		@Schema(description = "연락처", example = "01012345678")
		private String contactNumber;

		@Schema(description = "이메일", example = "test@test.com")
		private String email;

		@Schema(description = "상태", example = "ACTIVE")
		private AccountStatus status;

		public static FindProfileResponse of(Account account) {
			return FindProfileResponse.builder()
				.id(account.getId())
				.accountId(account.getAccountId())
				.name(account.getName())
				.contactNumber(account.getContactNumber())
				.email(account.getEmail())
				.status(account.getStatus())
				.build();
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ModifyProfileRequest {
		@Schema(description = "이름", example = "홍길동")
		@NotBlank(message = "이름은 필수 입력입니다.")
		private String name;

		@Schema(description = "비밀번호", example = "1234")
		@NotBlank(message = "비밀번호는 필수 입력입니다.")
		private String password;

		@Schema(description = "연락처(숫자만 입력)", example = "01012345678")
		@NotBlank(message = "연락처는 필수 입력입니다.")
		private String contactNumber;

		@Schema(description = "이메일", example = "test@test.com")
		@NotBlank(message = "이메일은 필수 입력입니다.")
		private String email;

		public ModifyProfile toModifyProfile(String encryptedPassword) {
			return ModifyProfile.builder()
				.name(name)
				.password(encryptedPassword)
				.contactNumber(contactNumber)
				.email(email)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ModifyAccountResponse {
		@Schema(description = "계정 ID", example = "1")
		private Long id;

		@Schema(description = "아이디", example = "test")
		private String accountId;

		@Schema(description = "이름", example = "홍길동")
		private String name;

		@Schema(description = "연락처", example = "01012345678")
		private String contactNumber;

		@Schema(description = "이메일", example = "test@test.com")
		private String email;

		@Schema(description = "상태", example = "ACTIVE")
		private AccountStatus status;

		public static ModifyAccountResponse of(Account account) {
			return ModifyAccountResponse.builder()
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
