package org.example.o2o.api.v1.controller.auth;

import org.example.o2o.api.v1.docs.auth.AccountDocsController;
import org.example.o2o.api.v1.dto.auth.AccountDto.FindProfileResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.ModifyAccountResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.ModifyProfileRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupAdminResponse;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.api.v1.dto.auth.AccountDto.SignupOwnerResponse;
import org.example.o2o.api.v1.service.auth.AccountService;
import org.example.o2o.common.dto.ApiResponse;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController implements AccountDocsController {

	private final AccountService accountService;

	@AdminAuthorize
	@PostMapping("/owner")
	public ApiResponse<SignupOwnerResponse> signupOwner(
		@RequestBody @Valid SignupOwnerRequest signupRequest) {
		SignupOwnerResponse response = accountService.signupOwner(signupRequest);
		return ApiResponse.success(response);
	}

	@PostMapping("/admin")
	public ApiResponse<SignupAdminResponse> signupAdmin(
		@RequestBody @Valid SignupAdminRequest signupRequest) {
		SignupAdminResponse response = accountService.signupAdmin(signupRequest);
		return ApiResponse.success(response);
	}

	@OwnerAuthorize
	@GetMapping("/{id}/profile")
	public ApiResponse<FindProfileResponse> findProfileInfo(
		@PathVariable Long id,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		userDetails.validateId(id);
		FindProfileResponse profileInfo = accountService.findProfileInfo(id);
		return ApiResponse.success(profileInfo);
	}

	@OwnerAuthorize
	@PatchMapping("/{id}/profile")
	public ApiResponse<ModifyAccountResponse> modifyProfile(
		@PathVariable Long id,
		@RequestBody @Valid ModifyProfileRequest modifyRequest,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		userDetails.validateId(id);
		ModifyAccountResponse response = accountService.modifyProfile(id, modifyRequest);
		return ApiResponse.success(response);
	}

}
