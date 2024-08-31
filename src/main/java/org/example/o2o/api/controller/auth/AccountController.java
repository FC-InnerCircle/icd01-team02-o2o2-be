package org.example.o2o.api.controller.auth;

import org.example.o2o.api.dto.auth.AccountDto.FindProfileResponse;
import org.example.o2o.api.dto.auth.AccountDto.ModifyAccountResponse;
import org.example.o2o.api.dto.auth.AccountDto.ModifyProfileRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupAdminResponse;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerResponse;
import org.example.o2o.api.service.auth.AccountService;
import org.example.o2o.config.exception.ErrorResponse;
import org.example.o2o.config.security.CustomUserDetails;
import org.example.o2o.config.security.annotation.AdminAuthorize;
import org.example.o2o.config.security.annotation.OwnerAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final AccountService accountService;

	@Operation(summary = "점주 회원가입", description = "점주 정보로 회원가입합니다.")
	@ApiResponse(responseCode = "200", description = "회원가입 후 등록된 회원 정보를 반환합니다.")
	@ApiResponse(responseCode = "409", description = "아이디가 중복된 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@AdminAuthorize
	@PostMapping("/owner")
	public org.example.o2o.common.dto.ApiResponse<SignupOwnerResponse> signupOwner(
		@RequestBody @Valid SignupOwnerRequest signupRequest) {
		SignupOwnerResponse response = accountService.signupOwner(signupRequest);
		return org.example.o2o.common.dto.ApiResponse.success(response);
	}

	@Operation(summary = "관리자 회원가입", description = "관리자 정보로 회원가입합니다.")
	@ApiResponse(responseCode = "200", description = "회원가입 후 등록된 회원 정보를 반환합니다.")
	@ApiResponse(responseCode = "409", description = "아이디가 중복된 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@PostMapping("/admin")
	public org.example.o2o.common.dto.ApiResponse<SignupAdminResponse> signupAdmin(
		@RequestBody @Valid SignupAdminRequest signupRequest) {
		SignupAdminResponse response = accountService.signupAdmin(signupRequest);
		return org.example.o2o.common.dto.ApiResponse.success(response);
	}

	@Operation(summary = "프로필 정보 조회", description = "계정 ID에 해당하는 프로필 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "계정의 프로필 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "아이디가 존재하지 않는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@OwnerAuthorize
	@GetMapping("/{id}/profile")
	public org.example.o2o.common.dto.ApiResponse<FindProfileResponse> findProfileInfo(
		@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		userDetails.validateId(id);
		FindProfileResponse profileInfo = accountService.findProfileInfo(id);
		return org.example.o2o.common.dto.ApiResponse.success(profileInfo);
	}

	@Operation(summary = "프로필 정보 변경", description = "계정 ID에 해당하는 프로필 정보를 변경합니다.")
	@ApiResponse(responseCode = "200", description = "프로필 정보를 변경하고 변경된 프로필 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "아이디가 존재하지 않는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	@OwnerAuthorize
	@PatchMapping("/{id}/profile")
	public org.example.o2o.common.dto.ApiResponse<ModifyAccountResponse> modifyProfile(
		@PathVariable Long id,
		@RequestBody @Valid ModifyProfileRequest modifyRequest,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		userDetails.validateId(id);
		ModifyAccountResponse response = accountService.modifyProfile(id, modifyRequest);
		return org.example.o2o.common.dto.ApiResponse.success(response);
	}

}
