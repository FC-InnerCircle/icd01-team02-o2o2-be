package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;

import org.example.o2o.api.dto.file.request.ImageFileCreateRequestDto;
import org.example.o2o.api.dto.file.response.ImageFileResponseDto;
import org.example.o2o.api.dto.menu.request.MenuCreateRequestDto;
import org.example.o2o.api.dto.menu.request.MenuOptionCreateRequestDto;
import org.example.o2o.api.dto.menu.request.MenuOptionGroupCreateDto;
import org.example.o2o.api.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.MenuFixture;
import org.example.o2o.fixture.StoreFixture;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CreateMenuServiceTest {

	@Autowired
	private MenuService menuService;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreMenuRepository menuRepository;

	@BeforeEach
	void setUp() {
		menuRepository.deleteAll();
		storeRepository.deleteAll();
	}

	@Test
	void 메뉴등록() {
		Store store = storeRepository.save(StoreFixture.createStore());
		StoreMenu menu = MenuFixture.createMenu(store, 1);

		MenuDetailResponseDto response = menuService.register(store.getId(), menu);

		assertThat(menuRepository.count()).isEqualTo(1);
		assertThat(response.name()).isEqualTo(menu.getName());
	}

	@Test
	void 메뉴등록_조회() {
		Store store = storeRepository.save(StoreFixture.createStore());
		StoreMenu menu = MenuFixture.createMenu(store, 1);

		MenuDetailResponseDto saveMenu = menuService.register(store.getId(), menu);
		MenuDetailResponseDto response = menuService.findStoreMenuDetail(saveMenu.menuId());

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

	@Test
	void 메뉴등록_dto() {
		Store store = storeRepository.save(StoreFixture.createStore());

		MenuOptionCreateRequestDto option1 = new MenuOptionCreateRequestDto(1, "1단계", "", 0);
		MenuOptionCreateRequestDto option2 = new MenuOptionCreateRequestDto(2, "2단계", "", 0);
		MenuOptionGroupCreateDto optionGroup = new MenuOptionGroupCreateDto(
			1,
			"맵기 선택",
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
			new MenuOptionGroupCreateDto[] {optionGroup},
			new ImageFileCreateRequestDto[] {image1, image2}
		);

		MenuDetailResponseDto response = menuService.register(store.getId(), menu.to());

		assertThat(menuRepository.count()).isEqualTo(1);
		assertThat(response.name()).isEqualTo(menu.name());
		assertThat(response.optionGroups().size()).isEqualTo(1);
		assertThat(response.optionGroups().get(0).options().get(0).name()).isEqualTo(option1.name());
		assertThat(response.optionGroups().get(0).options().get(1).name()).isEqualTo(option2.name());
		assertThat(response.images().get(0).imageUrl()).isEqualTo(image1.imageUrl());
		assertThat(response.images().get(1).imageUrl()).isEqualTo(image2.imageUrl());
	}
}
