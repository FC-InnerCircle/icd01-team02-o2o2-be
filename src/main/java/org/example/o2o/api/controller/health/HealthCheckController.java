package org.example.o2o.api.controller.health;

import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/health")
public class HealthCheckController {

	@GetMapping("/check")
	public ApiResponse<String> healthCheck() {
		return ApiResponse.success("success");
	}
}
