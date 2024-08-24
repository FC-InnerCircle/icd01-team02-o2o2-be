package org.example.o2o.api.controller.auth;

import org.example.o2o.api.dto.auth.AccountDto.SignupAdminRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupAdminResponse;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerRequest;
import org.example.o2o.api.dto.auth.AccountDto.SignupOwnerResponse;
import org.example.o2o.api.service.auth.AccountService;
import org.example.o2o.common.dto.ApiResponse;
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
@RequestMapping("/api/v1/account")
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/owner")
	public ApiResponse<SignupOwnerResponse> signupOwner(@RequestBody @Valid SignupOwnerRequest signupRequest) {
		SignupOwnerResponse response = accountService.signupOwner(signupRequest);
		return ApiResponse.success(response);
	}

	@PostMapping("/admin")
	public ApiResponse<SignupAdminResponse> signupAdmin(@RequestBody @Valid SignupAdminRequest signupRequest) {
		SignupAdminResponse response = accountService.signupAdmin(signupRequest);
		return ApiResponse.success(response);
	}
}
