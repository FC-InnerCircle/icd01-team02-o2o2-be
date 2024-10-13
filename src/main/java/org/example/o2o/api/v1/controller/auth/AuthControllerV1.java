package org.example.o2o.api.v1.controller.auth;

import org.example.o2o.api.v1.docs.auth.AuthDocsControllerV1;
import org.example.o2o.api.v1.dto.auth.AuthDto.LoginRequest;
import org.example.o2o.api.v1.dto.auth.AuthDto.LogoutRequest;
import org.example.o2o.api.v1.dto.auth.AuthDto.ReissueTokenRequest;
import org.example.o2o.api.v1.service.auth.AuthServiceV1;
import org.example.o2o.common.dto.ApiResponse;
import org.example.o2o.common.dto.jwt.TokenDto.TokenResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerV1 implements AuthDocsControllerV1 {

	private final AuthServiceV1 authService;

	@PostMapping("/login")
	public ApiResponse<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
		TokenResponse reesponse = authService.login(loginRequest);
		return ApiResponse.success(reesponse);
	}

	@PostMapping("/refresh")
	public ApiResponse<TokenResponse> refresh(
		@RequestBody @Valid ReissueTokenRequest reissueTokenRequest) {
		TokenResponse response = authService.reissueToken(reissueTokenRequest);
		return ApiResponse.success(response);
	}

	@PostMapping("/logout")
	public ApiResponse<Void> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
		authService.logout(logoutRequest);
		return ApiResponse.success();
	}

}
