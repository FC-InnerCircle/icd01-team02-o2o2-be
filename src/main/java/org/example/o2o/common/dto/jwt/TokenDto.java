package org.example.o2o.common.dto.jwt;

import org.example.o2o.domain.auth.Account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TokenResponse {
		@Schema(description = "액세스 토큰", example = "aaa.bbb.ccc")
		private String accessToken;

		@Schema(description = "리프레쉬 토큰", example = "aaa.bbb.ccc")
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
	public static class Payload {
		private String subject;

		private Long id;
		private String accountId;
		private String role;
		private String name;
	}
}
