package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;

import org.example.o2o.api.dto.file.response.ImageFileResponseDto;
import org.example.o2o.api.dto.menu.request.MenusRequestDto;
import org.example.o2o.api.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.api.dto.menu.response.MenuResponseDto;
import org.example.o2o.api.dto.menu.response.MenusResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.fixture.MenuFixture;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MenuServiceTest {

	@Autowired
	private MenuService menuService;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreMenuRepository menuRepository;

	private StoreMenu TEST_MENU = null;

	@BeforeEach
	void setUp() {
		menuRepository.deleteAll();
		TEST_MENU = menuRepository.save(MenuFixture.createMenu(storeRepository.save(MenuFixture.creatStore()), 1));
	}

	@Test
	void 메뉴목록_조회() {
		MenusRequestDto requestDto = new MenusRequestDto(0, 10, "ENABLED", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			TEST_MENU.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.totalLength()).isEqualTo(1);
		assertThat(storeMenus.menus().get(0)).isEqualTo(MenuResponseDto.of(TEST_MENU));
	}

	@Test
	void 메뉴목록_품절() {
		MenusRequestDto requestDto = new MenusRequestDto(0, 1, "SOLDOUT", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			TEST_MENU.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.totalLength()).isEqualTo(0);
	}

	@Test
	void 메뉴목록_페이징() {
		menuRepository.save(MenuFixture.createMenu(TEST_MENU.getStore(), 2));

		MenusRequestDto requestDto = new MenusRequestDto(0, 1, "", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			TEST_MENU.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.size()).isEqualTo(requestDto.size());
		assertThat(storeMenus.menus().size()).isEqualTo(1);
	}

	@Test
	void 메뉴목록_정렬_내림차순() {
		menuRepository.save(MenuFixture.createMenu(TEST_MENU.getStore(), 2));

		MenusRequestDto requestDto = new MenusRequestDto(0, 10, "", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			TEST_MENU.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.menus().size()).isEqualTo(2);
		assertThat(storeMenus.menus().get(0).ordering()).isEqualTo(2);
		assertThat(storeMenus.menus().get(1).ordering()).isEqualTo(1);
	}

	@Test
	void 메뉴목록_정렬_오름차순() {
		menuRepository.save(MenuFixture.createMenu(TEST_MENU.getStore(), 2));

		MenusRequestDto requestDto = new MenusRequestDto(0, 10, "", "ordering", Sort.Direction.ASC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			TEST_MENU.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.menus().size()).isEqualTo(2);
		assertThat(storeMenus.menus().get(0).ordering()).isEqualTo(1);
		assertThat(storeMenus.menus().get(1).ordering()).isEqualTo(2);
	}

	@Test
	void 메뉴상세정보_조회() {
		MenuDetailResponseDto menuDetail = menuService.findStoreMenuDetail(TEST_MENU.getId());

		assertThat(menuDetail.name()).isEqualTo(TEST_MENU.getName());
		assertThat(menuDetail.status()).isEqualTo(TEST_MENU.getStatus());
		assertThat(menuDetail.images()).containsAll(TEST_MENU.getImageFileGroup()
			.getDetails()
			.stream()
			.map(ImageFileResponseDto::of)
			.collect(Collectors.toList()));
		assertThat(menuDetail.optionGroups()).containsAll(TEST_MENU.getMenuOptionGroups()
			.stream()
			.map(MenuOptionGroupResponseDto::of)
			.collect(Collectors.toList()));
	}

	@Test
	void 메뉴상세정보_조회실패() {
		assertThatThrownBy(() -> menuService.findStoreMenuDetail(99L))
			.isInstanceOf(ApiException.class);
	}
}