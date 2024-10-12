package org.example.o2o.api.v1.docs.auth;

import org.example.o2o.api.v1.dto.auth.AuthDto.LoginRequest;
import org.example.o2o.api.v1.dto.auth.AuthDto.LogoutRequest;
import org.example.o2o.api.v1.dto.auth.AuthDto.ReissueTokenRequest;
import org.example.o2o.common.dto.jwt.TokenDto.TokenResponse;
import org.example.o2o.config.exception.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "인증 및 인가 API", description = "로그인 및 토큰발급 처리")
public interface AuthDocsController {

	@Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인합니다.")
	@ApiResponse(responseCode = "200", description = "로그인 성공시 생성된 액세스 토큰과 리프레쉬 토큰 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "아이디 또는 비밀번호가 잘못된 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "403", description = "계정이 비활성화 상태인 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<TokenResponse> login(
		LoginRequest loginRequest);

	@Operation(summary = "토큰 재발급", description = "리프레쉬 토큰을 검증하고 재발급합니다.")
	@ApiResponse(responseCode = "200", description = "리프레쉬 토큰 검증 성공시 생성된 액세스 토큰과 리프레쉬 토큰 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "토큰 정보가 올바르지 않거나, 아이디가 존재하지 않는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "403", description = "계정이 비활성화 상태인 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<TokenResponse> refresh(
		@RequestBody @Valid ReissueTokenRequest reissueTokenRequest);

	@Operation(summary = "로그아웃", description = "리프레쉬 토큰을 검증하고 로그아웃합니다.")
	@ApiResponse(responseCode = "200", description = "리프레쉬 토큰 검증 성공",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@ApiResponse(responseCode = "400", description = "토큰 정보가 올바르지 않은 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<Void> logout(
		@RequestBody @Valid LogoutRequest logoutRequest);
}
