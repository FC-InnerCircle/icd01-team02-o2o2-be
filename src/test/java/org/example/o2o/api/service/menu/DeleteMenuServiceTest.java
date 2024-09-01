package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.MenuFixture;
import org.example.o2o.repository.file.FileGroupRepository;
import org.example.o2o.repository.menu.StoreMenuOptionGroupRepository;
import org.example.o2o.repository.menu.StoreMenuOptionRepository;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DeleteMenuServiceTest {

	@Autowired
	private MenuService menuService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreMenuRepository menuRepository;

	@Autowired
	private FileGroupRepository fileGroupRepository;

	@Autowired
	private StoreMenuOptionRepository menuOptionRepository;

	@Autowired
	private StoreMenuOptionGroupRepository menuOptionGroupRepository;

	private Store testStore = null;

	private StoreMenu testMenu = null;

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
		fileGroupRepository.deleteAll();
		testStore = storeRepository.save(MenuFixture.creatStore());
		testMenu = menuRepository.save(MenuFixture.createMenu(testStore, 1));
	}

	@Test
	void testDeleteMenuSuccessful() {
		assertThat(menuRepository.count()).isEqualTo(1);

		menuService.delete(testMenu.getId());

		assertThat(menuRepository.count()).isEqualTo(0);
	}

	@Test
	void testDeleteMenuWithOptionsSuccessful() {
		Long imageGroupId = testMenu.getImageFileGroup().getId();
		int menuOptionGroupCount = testMenu.getMenuOptionGroups().size();

		assertThat(menuOptionGroupRepository.count()).isEqualTo(menuOptionGroupCount);

		menuService.delete(testMenu.getId());

		assertThat(fileGroupRepository.count()).isEqualTo(0);
		assertThat(menuOptionRepository.count()).isEqualTo(0);
		assertThat(menuOptionGroupRepository.count()).isEqualTo(0);
	}

	@Test
	void testDeleteMenuFail() {
		assertThatThrownBy(() -> menuService.delete(999L))
			.isInstanceOf(ApiException.class);
		;
	}
}
