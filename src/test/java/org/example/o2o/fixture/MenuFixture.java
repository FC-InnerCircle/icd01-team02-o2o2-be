package org.example.o2o.fixture;

import java.time.LocalDateTime;
import java.util.List;

import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.file.FileGroupType;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuOption;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.domain.store.Store;

public class MenuFixture {

	public static StoreMenu createMenu(Store store, Integer ordering) {

		return StoreMenu.builder()
			.menuOptionGroups(List.of(createMenuOptionGroup()))
			.imageFileGroup(createFileGroup())
			.name("테스트 메뉴")
			.description("테스트 설명")
			.price(10_000)
			.ordering(ordering)
			.store(store)
			.status(StoreMenuStatus.ENABLED)
			.build();
	}

	public static Store creatStore() {

		return Store.builder()
			.name("테스트 가게")
			.contactNumber("010-1234-5678")
			.zipCode("111-11")
			.address("테스트 주소")
			.addressDetail("테스트 상세 주소")
			.latitude("11.11")
			.longitude("99.99")
			.openTime("09:00")
			.closeTime("18:00")
			.category("테스트 카테고리")
			.deliveryArea("테스트 지역")
			.minimumOrderAmount(10000)
			.build();
	}

	private static FileGroup createFileGroup() {

		FileGroup fileGroup = FileGroup.builder()
			.groupType(FileGroupType.MENU)
			.build();

		FileDetail fileDetail1 = FileDetail.builder()
			.fileGroup(fileGroup)
			.ordering(1)
			.originalFileName("메뉴 사진1.jpg")
			.storedFileName("메뉴 사진1.jpg")
			.path("/store/menu")
			.extension(".jpg")
			.size("100mb")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		FileDetail fileDetail2 = FileDetail.builder()
			.fileGroup(fileGroup)
			.ordering(2)
			.originalFileName("메뉴 사진2.jpg")
			.storedFileName("메뉴 사진2.jpg")
			.path("/store/menu")
			.extension(".jpg")
			.size("200mb")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		fileGroup.getDetails().addAll(List.of(fileDetail1, fileDetail2));

		return fileGroup;
	}

	private static StoreMenuOptionGroup createMenuOptionGroup() {

		StoreMenuOptionGroup menuOptionGroup = StoreMenuOptionGroup.builder()
			.title("맵기 선택")
			.isRequired(true)
			.ordering(1)
			.build();

		StoreMenuOption option1 = StoreMenuOption.builder()
			.optionGroup(menuOptionGroup)
			.name("1단계 (신라면)")
			.description("")
			.price(0)
			.ordering(1)
			.build();

		StoreMenuOption option2 = StoreMenuOption.builder()
			.optionGroup(menuOptionGroup)
			.name("2단계 (불닭)")
			.description("")
			.price(0)
			.ordering(2)
			.build();

		StoreMenuOption option3 = StoreMenuOption.builder()
			.optionGroup(menuOptionGroup)
			.name("3단계 (땡초)")
			.description("")
			.price(1000)
			.ordering(3)
			.build();

		menuOptionGroup.getOptions().addAll(List.of(option1, option2, option3));

		return menuOptionGroup;
	}
}
