package org.example.o2o.api.v2.dto.review.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRegisterResponse {

	@Schema(description = "리뷰 아이디", example = "1")
	private Long reviewId;

	public static ReviewRegisterResponse of(Long reviewId) {
		return ReviewRegisterResponse.builder()
			.reviewId(reviewId)
			.build();
	}
}
