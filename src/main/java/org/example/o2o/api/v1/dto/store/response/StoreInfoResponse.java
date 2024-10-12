package org.example.o2o.api.v1.dto.store.response;

import org.example.o2o.domain.store.Store;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StoreInfoResponse(
	@Schema(description = "스토어 ID", example = "1")
	Long id,

	@Schema(description = "스토어 이름", example = "BBQ")
	String name,

	@Schema(description = "연락처 번호", example = "010-1234-5678")
	String contactNumber,

	@Schema(description = "우편번호", example = "12345")
	String zipCode,

	@Schema(description = "주소", example = "서울특별시 강남구 테헤란로 123")
	String address,

	@Schema(description = "상세 주소", example = "OO빌딩 3층")
	String addressDetail,

	@Schema(description = "위도", example = "37.5453458368972")
	Double latitude,

	@Schema(description = "경도", example = "127.18484365063364")
	Double longitude,

	@Schema(description = "오픈 시간", example = "10:00")
	String openTime,

	@Schema(description = "마감 시간", example = "23:00")
	String closeTime,

	@Schema(description = "카테고리 목록", example = "[\"치킨\", \"중식\"]")
	String[] categories,

	@Schema(description = "최소 주문 금액", example = "10000")
	Integer minimumOrderAmount,

	@Schema(description = "배달 지역", example = "금천구")
	String deliveryArea,

	@Schema(description = "생성 일시", example = "2024-01-01T12:00:00")
	String createdAt,

	@Schema(description = "수정 일시", example = "2024-01-02T15:30:00")
	String updatedAt) {

	public static StoreInfoResponse of(Store store) {
		return StoreInfoResponse.builder()
			.id(store.getId())
			.name(store.getName())
			.contactNumber(store.getContactNumber())
			.zipCode(store.getZipCode())
			.address(store.getAddress())
			.addressDetail(store.getAddressDetail())
			.latitude(store.getLatitude())
			.longitude(store.getLongitude())
			.openTime(store.getOpenTime())
			.closeTime(store.getCloseTime())
			.categories(store.getCategory().split(","))
			.minimumOrderAmount(store.getMinimumOrderAmount())
			.deliveryArea(store.getDeliveryArea())
			.createdAt(store.getCreatedAt().toString())
			.updatedAt(store.getUpdatedAt().toString())
			.build();
	}
}
