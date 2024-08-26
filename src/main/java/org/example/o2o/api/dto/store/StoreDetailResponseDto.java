package org.example.o2o.api.dto.store;

import org.example.o2o.domain.store.Store;

import lombok.Builder;

@Builder
public record StoreDetailResponseDto(
	Long id,
	String name,
	String contactNumber,
	String zipCode,
	String address,
	String addressDetail,
	Double latitude,
	Double longitude,
	String openTime,
	String closeTime,
	String[] categories,
	Integer minimumOrderAmount,
	String deliveryArea,
	String createdAt,
	String updatedAt) {

	public static StoreDetailResponseDto of(Store store) {
		return StoreDetailResponseDto.builder()
			.id(store.getId())
			.name(store.getName())
			.contactNumber(store.getContactNumber())
			.zipCode(store.getZipCode())
			.address(store.getAddress())
			.addressDetail(store.getAddressDetail())
			.latitude(Double.parseDouble(store.getLatitude()))
			.longitude(Double.parseDouble(store.getLongitude()))
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
