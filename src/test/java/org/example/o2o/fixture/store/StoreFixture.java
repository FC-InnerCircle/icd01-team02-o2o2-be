package org.example.o2o.fixture.store;

import java.util.ArrayList;

import org.example.o2o.domain.store.Store;
import org.example.o2o.domain.store.StoreStatus;

public class StoreFixture {

	public static Store createStore() {
		return Store.builder()
			.name("Test Store")
			.contactNumber("010-1234-5678")
			.zipCode("12345")
			.address("서울특별시 테스트구")
			.addressDetail("테스트동 123")
			.latitude(37.5665)
			.longitude(126.9780)
			.openTime("09:00")
			.closeTime("22:00")
			.category("Korean")
			.deliveryArea("테스트구")
			.minimumOrderAmount(10000)
			.status(StoreStatus.ACTIVE)
			.menus(new ArrayList<>())
			.build();
	}

	public static Store createCustomStore(String name, String category) {
		return Store.builder()
			.name(name)
			.contactNumber("010-1234-5678")
			.zipCode("12345")
			.address("서울특별시 테스트구")
			.addressDetail("테스트동 123")
			.latitude(37.5665)
			.longitude(126.9780)
			.openTime("09:00")
			.closeTime("22:00")
			.category(category)
			.deliveryArea("테스트구")
			.minimumOrderAmount(10000)
			.status(StoreStatus.ACTIVE)
			.menus(new ArrayList<>())
			.build();
	}
}
