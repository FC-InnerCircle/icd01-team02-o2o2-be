package org.example.o2o.api.controller.auth;

import org.example.o2o.api.dto.auth.AuthDto.LoginRequest;
import org.example.o2o.api.dto.auth.AuthDto.ReissueTokenRequest;
import org.example.o2o.api.service.auth.AuthService;
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
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}

	@PostMapping("/refresh")
	public TokenResponse refresh(@RequestBody @Valid ReissueTokenRequest reissueTokenRequest) {
		return authService.reissueToken(reissueTokenRequest);
	}

	@PostMapping("/logout")
	public String logout() {
		return "";
	}

}
