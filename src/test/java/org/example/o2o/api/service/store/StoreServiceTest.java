package org.example.o2o.api.service.store;

import static org.assertj.core.api.Assertions.*;

import org.example.o2o.api.dto.store.StoreDetailResponseDto;
import org.example.o2o.api.dto.store.StoreListRequestDto;
import org.example.o2o.api.dto.store.StoreListResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.StoreFixture;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class StoreServiceTest {

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreRepository storeRepository;

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
		storeRepository.save(StoreFixture.createStore());
	}

	@Test
	void testGetStoresSuccessful() {
		Store store = StoreFixture.createStore();
		storeRepository.save(store);

		StoreListRequestDto requestDto = new StoreListRequestDto(0, 10, "createdAt", Sort.Direction.DESC);
		Pageable pageable = PageRequest.of(requestDto.page(), requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField()));

		StoreListResponseDto responseDto = storeService.getStores(pageable);

		assertThat(responseDto.stores()).isNotEmpty();
		assertThat(responseDto.stores().get(0).name()).isEqualTo("Test Store");
	}

	@Test
	void testGetStoresPageSizeOne() {
		storeRepository.save(StoreFixture.createStore());
		storeRepository.save(StoreFixture.createCustomStore("Store 2", "Korean"));

		StoreListRequestDto requestDto = new StoreListRequestDto(0, 1, "createdAt", Sort.Direction.DESC);
		Pageable pageable = PageRequest.of(requestDto.page(), requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField()));

		StoreListResponseDto responseDto = storeService.getStores(pageable);

		assertThat(responseDto.stores()).hasSize(1);
	}

	@Test
	void testGetStoresSortByName() {
		storeRepository.save(StoreFixture.createCustomStore("B Store", "Korean"));
		storeRepository.save(StoreFixture.createCustomStore("A Store", "Korean"));

		StoreListRequestDto requestDto = new StoreListRequestDto(0, 10, "name", Sort.Direction.ASC);
		Pageable pageable = PageRequest.of(requestDto.page(), requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField()));

		StoreListResponseDto responseDto = storeService.getStores(pageable);

		assertThat(responseDto.stores().get(0).name()).isEqualTo("A Store");
	}

	@Test
	void testGetStoreByIdSuccessful() {
		Store store = storeRepository.save(StoreFixture.createStore());
		StoreDetailResponseDto responseDto = storeService.getStoreById(store.getId());

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.name()).isEqualTo("Test Store");
	}

	@Test
	void testDeleteStoreByIdSuccessful() {
		Store store = storeRepository.save(StoreFixture.createStore());
		StoreDetailResponseDto responseDto = storeService.getStoreById(store.getId());

		assertThat(responseDto).isNotNull();
		storeService.deleteStoreById(store.getId());

		assertThatThrownBy(() -> storeService.getStoreById(store.getId()))
			.isInstanceOf(ApiException.class);
	}

}
