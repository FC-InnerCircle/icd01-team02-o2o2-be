package org.example.o2o.api.v1.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoginRequest {
		@Schema(description = "아이디", example = "test")
		@NotBlank(message = "아이디는 필수 입력입니다.")
		private String accountId;

		@Schema(description = "비밀번호", example = "1234")
		@NotBlank(message = "비밀번호는 필수 입력입니다.")
		private String password;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ReissueTokenRequest {
		@Schema(description = "리프레쉬 토큰", example = "aaa.bbb.ccc")
		@NotBlank(message = "리프레쉬 토큰은 필수 입력입니다.")
		@NotBlank
		private String refreshToken;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LogoutRequest {
		@Schema(description = "리프레쉬 토큰", example = "aaa.bbb.ccc")
		@NotBlank(message = "리프레쉬 토큰은 필수 입력입니다.")
		@NotBlank
		private String refreshToken;
	}
}
