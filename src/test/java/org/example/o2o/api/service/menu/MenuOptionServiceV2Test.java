package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.example.o2o.api.v2.dto.menu.request.MenuOptionCreateRequestDto;
import org.example.o2o.api.v2.dto.menu.request.MenuOptionGroupCreateRequestDto;
import org.example.o2o.api.v2.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.api.v2.service.menu.MenuOptionServiceV2;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.menu.MenuFixture;
import org.example.o2o.fixture.store.StoreFixture;
import org.example.o2o.repository.menu.StoreMenuOptionGroupRepository;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MenuOptionServiceV2Test {

	@Autowired
	private MenuOptionServiceV2 menuOptionServiceV2;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreMenuRepository menuRepository;

	@Autowired
	private StoreMenuOptionGroupRepository menuOptionGroupRepository;

	private Store testStore = StoreFixture.createStore();

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
	}

	@DisplayName("메뉴 옵션 등록")
	@Test
	void testCreateSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		int menuOptionGroupCount = menu.getMenuOptionGroups().size();

		MenuOptionCreateRequestDto option1 = new MenuOptionCreateRequestDto(1, "옵션 내용 1", 1_000);
		MenuOptionCreateRequestDto option2 = new MenuOptionCreateRequestDto(2, "옵션 내용 2", 2_000);
		MenuOptionGroupCreateRequestDto optionGroup = new MenuOptionGroupCreateRequestDto(menuOptionGroupCount + 1,
			"옵션 2", true, true,
			new MenuOptionCreateRequestDto[] {option1, option2});

		StoreMenuOptionGroup storeMenuOptionGroup = optionGroup.toStoreMenuOptionGroup();
		menuOptionServiceV2.register(menu.getId(), storeMenuOptionGroup);

		List<MenuOptionGroupResponseDto> menuOptions = menuOptionGroupRepository.findAll()
			.stream()
			.map(MenuOptionGroupResponseDto::of)
			.toList();

		assertThat(menuOptionGroupRepository.count()).isEqualTo(menuOptionGroupCount + 1);
		assertThat(menuOptions).containsAnyOf(MenuOptionGroupResponseDto.of(storeMenuOptionGroup));
	}

	@DisplayName("메뉴 옵션 삭제")
	@Test
	void testDeleteSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		assertThat(menuOptionGroupRepository.count()).isGreaterThan(0);

		Long menuOptionId = menu.getMenuOptionGroups().get(0).getId();

		menuOptionServiceV2.delete(menuOptionId);

		assertThat(menuOptionGroupRepository.findByIdAndIsDeletedFalse(menuOptionId).isEmpty()).isTrue();

		assertThatThrownBy(() -> menuOptionServiceV2.delete(menuOptionId))
			.isInstanceOf(ApiException.class);
	}

	@DisplayName("메뉴 옵션 수정 - 메뉴 옵션 그룹 변경")
	@Test
	void testUpdateOptionGroupSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		StoreMenuOptionGroup menuOptionGroup = menu.getMenuOptionGroups().get(0);

		MenuOptionCreateRequestDto afterOption = MenuOptionCreateRequestDto.builder()
			.name("")
			.price(0)
			.ordering(1)
			.build();

		MenuOptionGroupCreateRequestDto afterOptionGroup = MenuOptionGroupCreateRequestDto.builder()
			.options(new MenuOptionCreateRequestDto[] {afterOption})
			.title("메뉴 그룹명 수정")
			.ordering(1)
			.isRequired(false)
			.build();

		MenuOptionGroupResponseDto response = menuOptionServiceV2.update(menuOptionGroup.getId(),
			afterOptionGroup.toStoreMenuOptionGroup());

		assertThat(response.title()).isEqualTo("메뉴 그룹명 수정");
		assertThat(response.ordering()).isEqualTo(1);
		assertThat(response.isRequired()).isFalse();
	}

	@DisplayName("메뉴 옵션 수정 - 메뉴 옵션 변경")
	@Test
	void testUpdateOptionGroupWithOptionsSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		StoreMenuOptionGroup menuOptionGroup = menu.getMenuOptionGroups().get(0);

		MenuOptionCreateRequestDto afterOption = MenuOptionCreateRequestDto.builder()
			.name("이름 수정")
			.price(1000)
			.ordering(1)
			.build();

		MenuOptionGroupCreateRequestDto afterOptionGroup = MenuOptionGroupCreateRequestDto.builder()
			.options(new MenuOptionCreateRequestDto[] {afterOption})
			.title(menuOptionGroup.getTitle())
			.ordering(menuOptionGroup.getOrdering())
			.isRequired(menuOptionGroup.getIsRequired())
			.build();

		MenuOptionGroupResponseDto response = menuOptionServiceV2.update(menuOptionGroup.getId(),
			afterOptionGroup.toStoreMenuOptionGroup());

		assertThat(response.title()).isEqualTo(afterOptionGroup.title());
		assertThat(response.isRequired()).isEqualTo(afterOptionGroup.isRequired());
		assertThat(response.options().size()).isEqualTo(1);
		assertThat(response.options().get(0).name()).isEqualTo("이름 수정");
		assertThat(response.options().get(0).price()).isEqualTo(1000);
		assertThat(response.options().get(0).price()).isEqualTo(afterOption.price());
	}

	@DisplayName("메뉴 옵션 수정 실패 - 옵션 없음")
	@Test
	void testUpdateOptionGroupFail_menuOptionsNullOrEmpty() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		StoreMenuOptionGroup menuOptionGroup = menu.getMenuOptionGroups().get(0);

		MenuOptionGroupCreateRequestDto afterOptionGroupEmpty = MenuOptionGroupCreateRequestDto.builder()
			.options(new MenuOptionCreateRequestDto[] {})
			.title(menuOptionGroup.getTitle())
			.ordering(menuOptionGroup.getOrdering())
			.isRequired(menuOptionGroup.getIsRequired())
			.build();

		MenuOptionGroupCreateRequestDto afterOptionGroupNull = MenuOptionGroupCreateRequestDto.builder()
			.options(null)
			.title(menuOptionGroup.getTitle())
			.ordering(menuOptionGroup.getOrdering())
			.isRequired(menuOptionGroup.getIsRequired())
			.build();

		assertThatThrownBy(
			() -> menuOptionServiceV2.update(menuOptionGroup.getId(), afterOptionGroupEmpty.toStoreMenuOptionGroup()))
			.isInstanceOf(ApiException.class);

		assertThatThrownBy(
			() -> menuOptionServiceV2.update(menuOptionGroup.getId(), afterOptionGroupNull.toStoreMenuOptionGroup()))
			.isInstanceOf(ApiException.class);
	}
}
