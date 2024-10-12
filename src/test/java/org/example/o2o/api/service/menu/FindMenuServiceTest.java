package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;

import org.example.o2o.api.v1.dto.file.response.ImageFileResponseDto;
import org.example.o2o.api.v1.dto.menu.request.MenusRequestDto;
import org.example.o2o.api.v1.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.v1.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.api.v1.dto.menu.response.MenuResponseDto;
import org.example.o2o.api.v1.dto.menu.response.MenusResponseDto;
import org.example.o2o.api.v1.service.menu.MenuService;
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
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class FindMenuServiceTest {

	@Autowired
	private MenuService menuService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreMenuRepository menuRepository;

	private Store testStore = null;

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
		testStore = storeRepository.save(StoreFixture.createStore());
	}

	@DisplayName("메뉴 목록 조회")
	@Test
	void testFindMenusSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		MenusRequestDto requestDto = new MenusRequestDto(0, 10, "ENABLED", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			menu.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.totalLength()).isEqualTo(1);
		assertThat(storeMenus.menus().get(0)).isEqualTo(MenuResponseDto.of(menu));
	}

	@DisplayName("메뉴 목록 조회 - 품절")
	@Test
	void testFindMenusStatusInSoldOutSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		MenusRequestDto requestDto = new MenusRequestDto(0, 1, "SOLDOUT", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			menu.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.totalLength()).isEqualTo(0);
	}

	@DisplayName("메뉴 목록 조회 - 페이징")
	@Test
	void testFindMenusByPagingSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		menuRepository.save(MenuFixture.createMenu(menu.getStore(), 2));

		MenusRequestDto requestDto = new MenusRequestDto(0, 1, "", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			menu.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.size()).isEqualTo(requestDto.size());
		assertThat(storeMenus.menus().size()).isEqualTo(1);
	}

	@DisplayName("메뉴 목록 정렬 - 내림차순")
	@Test
	void testFindMenusOrderByDescSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		menuRepository.save(MenuFixture.createMenu(menu.getStore(), 2));

		MenusRequestDto requestDto = new MenusRequestDto(0, 10, "", "ordering", Sort.Direction.DESC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			menu.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.menus().size()).isEqualTo(2);
		assertThat(storeMenus.menus().get(0).ordering()).isEqualTo(2);
		assertThat(storeMenus.menus().get(1).ordering()).isEqualTo(1);
	}

	@DisplayName("메뉴 목록 정렬 - 오름차순")
	@Test
	void testFindMenusOrderByAscSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		menuRepository.save(MenuFixture.createMenu(menu.getStore(), 2));

		MenusRequestDto requestDto = new MenusRequestDto(0, 10, "", "ordering", Sort.Direction.ASC);

		MenusResponseDto storeMenus = menuService.findStoreMenus(
			menu.getStore().getId(),
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		assertThat(storeMenus.menus().size()).isEqualTo(2);
		assertThat(storeMenus.menus().get(0).ordering()).isEqualTo(1);
		assertThat(storeMenus.menus().get(1).ordering()).isEqualTo(2);
	}

	@DisplayName("메뉴 상세정보 조회")
	@Test
	void testFindMenuDetailSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		MenuDetailResponseDto menuDetail = menuService.findStoreMenuDetail(menu.getId());

		assertThat(menuDetail.name()).isEqualTo(menu.getName());
		assertThat(menuDetail.status()).isEqualTo(menu.getStatus());
		assertThat(menuDetail.images()).containsAll(menu.getImageFileGroup()
			.getDetails()
			.stream()
			.map(ImageFileResponseDto::of)
			.collect(Collectors.toList()));
		assertThat(menuDetail.optionGroups()).containsAll(menu.getMenuOptionGroups()
			.stream()
			.map(MenuOptionGroupResponseDto::of)
			.collect(Collectors.toList()));
	}

	@DisplayName("메뉴 상세정보 조회 - 실패")
	@Test
	void testFindMenuDetailFail() {
		assertThatThrownBy(() -> menuService.findStoreMenuDetail(99L))
			.isInstanceOf(ApiException.class);
	}
}
