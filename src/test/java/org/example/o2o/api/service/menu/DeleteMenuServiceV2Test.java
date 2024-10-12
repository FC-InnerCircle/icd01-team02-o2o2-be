package org.example.o2o.api.service.menu;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.example.o2o.api.v2.service.menu.MenuServiceV2;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.menu.MenuFixture;
import org.example.o2o.fixture.store.StoreFixture;
import org.example.o2o.repository.file.FileGroupRepository;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DeleteMenuServiceV2Test {

	@Autowired
	private MenuServiceV2 menuServiceV2;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreMenuRepository menuRepository;

	@Autowired
	private FileGroupRepository fileGroupRepository;

	private Store testStore = null;

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
		fileGroupRepository.deleteAll();
		testStore = storeRepository.save(StoreFixture.createStore());
	}

	@Test
	void testDeleteMenuSuccessful() {
		StoreMenu menu = menuRepository.save(MenuFixture.createMenu(testStore, 1));

		assertThat(menuRepository.count()).isEqualTo(1);
		assertThat(menu.getStatus()).isEqualTo(StoreMenuStatus.ENABLED);

		menuServiceV2.delete(menu.getId());

		List<StoreMenu> menus = menuRepository.findAll();
		StoreMenu findMenu = menus.get(0);

		assertThat(findMenu.getStatus()).isEqualTo(StoreMenuStatus.DISABLED);
	}

	@Test
	void testDeleteMenuFail() {
		assertThatThrownBy(() -> menuServiceV2.delete(999L))
			.isInstanceOf(ApiException.class);
	}
}
