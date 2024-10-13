package org.example.o2o.api.v2.dto.review.request;

import java.util.List;

import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.review.Review;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRegisterRequest {
	@Schema(description = "회원 아이디", example = "1")
	@NotNull(message = "회원 아이디는 필수 입력입니다.")
	private Long memberId;

	@Schema(description = "주문 번호", example = "1")
	@NotNull(message = "주문 번호는 필수 입력입니다.")
	private Long orderId;

	@Schema(description = "리뷰 내용", example = "맛있어요.")
	@NotEmpty(message = "내용은 필수 입력입니다.")
	@JsonProperty("contents")
	private String content;

	@Schema(description = "평점", example = "4")
	@Min(value = 0, message = "평점은 0 ~ 5점 이내여야 합니다.")
	@Max(value = 5, message = "평점은 0 ~ 5점 이내여야 합니다.")
	private int rating;

	@Schema(description = "리뷰 이미지 목록",
		example = """
				[{"url", "url2", "url3"}]
			""")
	private List<String> reviewImage;

	public Review toReview(OrderInfo orderInfo) {
		return Review.builder()
			.order(orderInfo)
			.store(orderInfo.getStore())
			.memeber(orderInfo.getMember())
			.rateScore(rating)
			.comment(content)
			.build();
	}
}
