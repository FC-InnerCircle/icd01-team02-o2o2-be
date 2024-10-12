package org.example.o2o.api.v1.dto.store.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.store.Store;
import org.example.o2o.domain.store.StoreCategory;
import org.springframework.util.ObjectUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreListResponseDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StoreListResponse {
		private List<StoresInfoResponse> stores;

		public static StoreListResponse of(List<Store> stores) {
			return StoreListResponse.builder()
				.stores(
					stores.stream()
						.map(StoresInfoResponse::of)
						.toList()
				)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StoresInfoResponse {
		@Schema(description = "스토어 ID", example = "1")
		private Long storeId;

		@Schema(description = "스토어 이름", example = "BBQ")
		private String storeName;

		@Schema(description = "최소 주문 금액", example = "10000")
		private int minimumPrice;

		@Schema(description = "배달 금액", example = "4000")
		private int deliveryPrice;

		@Schema(description = "리뷰 갯수", example = "12")
		private int reviewCount;

		@Schema(description = "리뷰 평점", example = "4.7")
		private float reviewRate;

		@Schema(description = "썸네일", example = "[\"https://s3.amazonaws.com/your-bucket-name/images/thumbnails/thumb1_abc123.jpg\", \"https://s3.amazonaws.com/your-bucket-name/images/thumbnails/thumb1_abc123.jpg\"]")
		private List<String> thumbnails;

		@Schema(description = "카테고리", example = "치킨")
		private List<String> category;

		public static StoresInfoResponse of(Store store) {
			return StoresInfoResponse.builder()
				.storeId(store.getId())
				.storeName(store.getName())
				.minimumPrice(store.getMinimumOrderAmount())
				.deliveryPrice(store.getMinimumOrderAmount())
				.reviewCount(
					ObjectUtils.isEmpty(store.getStoreRateScore()) ? 0 : store.getStoreRateScore().getCount()
				)
				.reviewRate(
					ObjectUtils.isEmpty(store.getStoreRateScore()) ? 0 : store.getStoreRateScore().getRating()
				)
				.thumbnails(
					ObjectUtils.isEmpty(store.getThumbnailFileGroup()) || ObjectUtils.isEmpty(
						store.getThumbnailFileGroup().getDetails()) ? new ArrayList<>() :
						store.getThumbnailFileGroup().getDetails().stream()
							.map(FileDetail::getFileAccessUrl)
							.toList()
				)
				.category(
					Arrays.stream(store.getCategory().split(","))
						.map(category -> StoreCategory.valueOf(category).getText())
						.toList()
				)
				.build();
		}

	}
}
