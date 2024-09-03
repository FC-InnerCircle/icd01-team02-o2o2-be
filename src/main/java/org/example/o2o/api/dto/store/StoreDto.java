package org.example.o2o.api.dto.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.config.exception.validator.EnumValue;
import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.store.Store;
import org.example.o2o.domain.store.StoreCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StoreDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StoreSaveRequest {
		@Schema(description = "상점명", example = "BBQ")
		@NotBlank(message = "상점명은 필수 입력입니다.")
		private String name;

		@Schema(description = "연락처", example = "01012345678")
		@NotBlank(message = "연락처는 필수 입력입니다.")
		private String contactNumber;

		@Schema(description = "우편번호", example = "12345")
		@NotBlank(message = "우편번호는 필수 입력입니다.")
		private String zipCode;

		@Schema(description = "주소", example = "서울 금천구 벚꽃로 309")
		@NotBlank(message = "주소는 필수 입력입니다.")
		private String address;

		@Schema(description = "상세주소", example = "1층 101호")
		@NotBlank(message = "상세주소는 필수 입력입니다.")
		private String addressDetail;

		@Schema(description = "위도", example = "37.5453458368972")
		@NotBlank(message = "위도는 필수 입력입니다.")
		private String latitude;

		@Schema(description = "경도", example = "127.18484365063364")
		@NotBlank(message = "경도는 필수 입력입니다.")
		private String longitude;

		@Schema(description = "오픈 시간", example = "10:00")
		@NotBlank(message = "오픈 시간은 필수 입력입니다.")
		private String openTime;

		@Schema(description = "마감 시간", example = "23:00")
		@NotBlank(message = "마감 시간은 필수 입력입니다.")
		private String closeTime;

		@Schema(description = "카테고리 목록", example = "['CAFE', 'CHICKEN', 'KOREAN_FOOD']")
		@EnumValue(enumClass = StoreCategory.class, message = "카테고리가 올바르지 않습니다. {enumValues}")
		private List<StoreCategory> categories = new ArrayList<>();

		@Schema(description = "최소 주문 금액", example = "10000")
		private int minimumOrderAmount;

		public Store toStore() {
			return Store.builder()
				.name(name)
				.contactNumber(contactNumber)
				.zipCode(zipCode)
				.address(address)
				.addressDetail(addressDetail)
				.category(
					categories.stream()
						.map(StoreCategory::name)
						.collect(Collectors.joining(","))
				)
				.latitude(latitude)
				.longitude(longitude)
				.openTime(openTime)
				.closeTime(closeTime)
				.minimumOrderAmount(minimumOrderAmount)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StoreSaveResponse {
		private Long id;
		private String name;
		private String contactNumber;
		private String zipCode;
		private String address;
		private String addressDetail;
		private double latitude;
		private double longitude;
		private String openTime;
		private String closeTime;
		private List<StoreCategory> categories;
		private int minimumOrderAmount;
		private List<String> imageUrls;

		public static StoreSaveResponse of(Store store) {
			return StoreSaveResponse.builder()
				.id(store.getId())
				.name(store.getName())
				.contactNumber(store.getContactNumber())
				.zipCode(store.getZipCode())
				.address(store.getAddress())
				.addressDetail(store.getAddressDetail())
				.categories(
					Arrays.stream(store.getCategory().split(","))
						.map(StoreCategory::valueOf)
						.toList()
				)
				.latitude(Double.parseDouble(store.getLatitude()))
				.longitude(Double.parseDouble(store.getLongitude()))
				.openTime(store.getOpenTime())
				.closeTime(store.getCloseTime())
				.imageUrls(
					store.getThumbnailFileGroup()
						.getDetails().stream()
						.map(FileDetail::getFileAccessUrl)
						.toList()
				)
				.build();
		}
	}
}
