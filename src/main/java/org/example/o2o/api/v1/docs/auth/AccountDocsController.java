package org.example.o2o.api.v1.docs.auth;

import org.example.o2o.api.v1.dto.auth.AccountDto.FindProfileResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.ModifyAccountResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.ModifyProfileRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupAdminResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupOwnerResponse;
import org.example.o2o.config.exception.ErrorResponse;
import org.example.o2o.config.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "계정 관리 API", description = "회원가입 및 프로필 정보 관련 API")
public interface AccountDocsController {

	@Operation(summary = "점주 회원가입", description = "점주 정보로 회원가입합니다.")
	@ApiResponse(responseCode = "200", description = "회원가입 후 등록된 회원 정보를 반환합니다.")
	@ApiResponse(responseCode = "409", description = "아이디가 중복된 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<SignupOwnerResponse> signupOwner(
		@RequestBody @Valid SignupOwnerRequest signupRequest);

	@Operation(summary = "관리자 회원가입", description = "관리자 정보로 회원가입합니다.")
	@ApiResponse(responseCode = "200", description = "회원가입 후 등록된 회원 정보를 반환합니다.")
	@ApiResponse(responseCode = "409", description = "아이디가 중복된 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<SignupAdminResponse> signupAdmin(
		@RequestBody @Valid SignupAdminRequest signupRequest);

	@Operation(summary = "프로필 정보 조회", description = "계정 ID에 해당하는 프로필 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "계정의 프로필 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "아이디가 존재하지 않는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<FindProfileResponse> findProfileInfo(
		@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetails userDetails);

	@Operation(summary = "프로필 정보 변경", description = "계정 ID에 해당하는 프로필 정보를 변경합니다.")
	@ApiResponse(responseCode = "200", description = "프로필 정보를 변경하고 변경된 프로필 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "아이디가 존재하지 않는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<ModifyAccountResponse> modifyProfile(
		@PathVariable Long id,
		@RequestBody @Valid ModifyProfileRequest modifyRequest,
		@AuthenticationPrincipal CustomUserDetails userDetails);

}
