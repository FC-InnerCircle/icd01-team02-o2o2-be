package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.example.o2o.api.v1.dto.file.request.ImageFileCreateRequestDto;
import org.example.o2o.api.v1.dto.file.response.ImageFileResponseDto;
import org.example.o2o.api.v2.dto.menu.request.MenuCreateRequestDto;
import org.example.o2o.api.v2.dto.menu.request.MenuOptionCreateRequestDto;
import org.example.o2o.api.v2.dto.menu.request.MenuOptionGroupCreateRequestDto;
import org.example.o2o.api.v2.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.v2.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.api.v2.service.menu.MenuServiceV2;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.menu.MenuFixture;
import org.example.o2o.fixture.store.StoreFixture;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CreateMenuServiceV2Test {

	@Autowired
	private MenuServiceV2 menuServiceV2;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreMenuRepository menuRepository;

	private Store testStore = null;

	private final MockMultipartFile mockImageFile = new MockMultipartFile(
		"thumbnailFile",
		"thumbnail.jpg",
		"text/plain",
		"thumbnail file".getBytes(StandardCharsets.UTF_8));

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
		testStore = storeRepository.save(StoreFixture.createStore());
	}

	@DisplayName("메뉴 등록 성공")
	@Test
	void testCreateMenuSuccessful() {
		StoreMenu menu = MenuFixture.createMenu(testStore, 1);
		MenuDetailResponseDto response = menuServiceV2.register(testStore.getId(), List.of(mockImageFile), menu);

		assertThat(menuRepository.count()).isEqualTo(1);
		assertThat(response.name()).isEqualTo(menu.getName());
	}

	@DisplayName("메뉴 등록 후 조회 성공")
	@Test
	void testCreateAndFindMenuSuccessful() {
		StoreMenu menu = MenuFixture.createMenu(testStore, 1);
		MenuDetailResponseDto saveMenu = menuServiceV2.register(testStore.getId(), List.of(mockImageFile), menu);
		MenuDetailResponseDto response = menuServiceV2.findStoreMenuDetail(saveMenu.menuId());

		assertThat(response.menuId()).isEqualTo(saveMenu.menuId());
		assertThat(response.name()).isEqualTo(menu.getName());
		assertThat(response.optionGroups()).containsAll(menu.getMenuOptionGroups()
			.stream()
			.map(MenuOptionGroupResponseDto::of)
			.collect(Collectors.toList()));
		assertThat(response.images()).containsAll(menu.getImageFileGroup()
			.getDetails()
			.stream()
			.map(ImageFileResponseDto::of)
			.collect(Collectors.toList()));
	}

	@DisplayName("메뉴 DTO로 등록")
	@Test
	void testCreateMenuByDtoSuccessful() {
		MenuOptionCreateRequestDto option1 = new MenuOptionCreateRequestDto(1, "1단계", 0);
		MenuOptionCreateRequestDto option2 = new MenuOptionCreateRequestDto(2, "2단계", 0);
		MenuOptionGroupCreateRequestDto optionGroup = new MenuOptionGroupCreateRequestDto(
			1,
			"맵기 선택",
			true,
			true,
			new MenuOptionCreateRequestDto[] {option1, option2}
		);

		ImageFileCreateRequestDto image1 = new ImageFileCreateRequestDto(1, "/test/image1.jpg");
		ImageFileCreateRequestDto image2 = new ImageFileCreateRequestDto(2, "/test/image2.jpg");

		MenuCreateRequestDto menu = new MenuCreateRequestDto(
			StoreMenuStatus.ENABLED,
			"실비 김치",
			"매운 실비 김치",
			10_000,
			1,
			new MenuOptionGroupCreateRequestDto[] {optionGroup},
			new ImageFileCreateRequestDto[] {image1, image2}
		);

		MenuDetailResponseDto response = menuServiceV2.register(testStore.getId(), List.of(mockImageFile),
			menu.toStoreMenu());

		assertThat(menuRepository.count()).isEqualTo(1);
		assertThat(response.name()).isEqualTo(menu.name());
		assertThat(response.optionGroups().size()).isEqualTo(1);
		assertThat(response.optionGroups().get(0).options().get(0).name()).isEqualTo(option1.name());
		assertThat(response.optionGroups().get(0).options().get(1).name()).isEqualTo(option2.name());
	}

	@Test
	void testCreateMenuFail_store404() {
		StoreMenu menu = MenuFixture.createMenu(testStore, 1);
		menuServiceV2.register(testStore.getId(), List.of(mockImageFile), menu);

		assertThatThrownBy(() -> menuServiceV2.register(999L, List.of(mockImageFile), menu))
			.isInstanceOf(ApiException.class);
	}
}
