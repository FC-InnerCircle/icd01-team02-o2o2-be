package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.example.o2o.api.dto.menu.request.MenuOptionCreateRequestDto;
import org.example.o2o.api.dto.menu.request.MenuOptionGroupCreateDto;
import org.example.o2o.api.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.example.o2o.fixture.StoreFixture;
import org.example.o2o.fixture.menu.MenuFixture;
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
public class MenuOptionServiceTest {

	@Autowired
	private MenuOptionService menuOptionService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreMenuRepository menuRepository;

	@Autowired
	private StoreMenuOptionGroupRepository menuOptionGroupRepository;

	private StoreMenu testMenu = null;

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
		testMenu = menuRepository.save(MenuFixture.createMenu(StoreFixture.createStore(), 1));
	}

	@DisplayName("메뉴 옵션 등록")
	@Test
	void testCreateSuccessful() {
		int menuOptionGroupCount = testMenu.getMenuOptionGroups().size();

		MenuOptionCreateRequestDto option1 = new MenuOptionCreateRequestDto(1, "옵션 내용 1", "", 1_000);
		MenuOptionCreateRequestDto option2 = new MenuOptionCreateRequestDto(2, "옵션 내용 2", "", 2_000);
		MenuOptionGroupCreateDto optionGroup = new MenuOptionGroupCreateDto(menuOptionGroupCount + 1,
			"옵션 2", true,
			new MenuOptionCreateRequestDto[] {option1, option2});

		StoreMenuOptionGroup storeMenuOptionGroup = optionGroup.toStoreMenuOptionGroup();
		menuOptionService.register(testMenu.getId(), storeMenuOptionGroup);

		List<MenuOptionGroupResponseDto> menuOptions = menuOptionGroupRepository.findAll()
			.stream()
			.map(MenuOptionGroupResponseDto::of)
			.toList();

		assertThat(menuOptionGroupRepository.count()).isEqualTo(menuOptionGroupCount + 1);
		assertThat(menuOptions).containsAnyOf(MenuOptionGroupResponseDto.of(storeMenuOptionGroup));
	}
}
