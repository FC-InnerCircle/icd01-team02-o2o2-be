package org.example.o2o.common.dto.jwt;

import org.example.o2o.domain.auth.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TokenResponse {
		private String accessToken;
		private String refreshToken;
	}

	@Getter
	@NoArgsConstructor
	public static class AccessTokenClaimsInfo {
		private Long id;
		private String accountId;
		private String role;
		private String name;

		@Builder
		public AccessTokenClaimsInfo(Long id, String accountId, String role, String name) {
			this.id = id;
			this.accountId = accountId;
			this.role = role;
			this.name = name;
		}

		public static AccessTokenClaimsInfo of(Account account) {
			return AccessTokenClaimsInfo.builder()
				.id(account.getId())
				.accountId(account.getAccountId())
				.role(account.getRole().name())
				.name(account.getName())
				.build();
		}
	}

	@Getter
	@NoArgsConstructor
	public class Payload {
		private String subject;

		private Long id;
		private String accountId;
		private String role;
		private String name;
	}
}
