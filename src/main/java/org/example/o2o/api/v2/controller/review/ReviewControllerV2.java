package org.example.o2o.api.v2.controller.review;

import org.example.o2o.api.v2.dto.review.request.ReviewRegisterRequest;
import org.example.o2o.api.v2.dto.review.response.ReviewRegisterResponse;
import org.example.o2o.api.v2.service.review.ReviewServiceV2;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/reviews")
public class ReviewControllerV2 {

	private final ReviewServiceV2 reviewService;

	@PostMapping("")
	public ApiResponse<ReviewRegisterResponse> registerReview(@RequestBody @Valid ReviewRegisterRequest request) {
		ReviewRegisterResponse response = reviewService.registerReview(request);
		return ApiResponse.success(response);
	}
}
