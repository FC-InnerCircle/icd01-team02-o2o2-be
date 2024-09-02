package org.example.o2o.api.service.store;

import org.example.o2o.api.dto.store.StoreDetailResponseDto;
import org.example.o2o.api.dto.store.StoreListResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.store.Store;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StoreService {

	private final StoreRepository storeRepository;

	@Autowired
	public StoreService(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}

	public StoreListResponseDto getStores(Pageable pageable) {
		Page<Store> storePage = storeRepository.findAllByOrderByCreatedAtDesc(pageable);
		return StoreListResponseDto.of(storePage);
	}

	public StoreDetailResponseDto getStoreById(Long id) {
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));
		return StoreDetailResponseDto.of(store);
	}

	@Transactional
	public void deleteStoreById(Long id) {
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));
		storeRepository.delete(store);
	}
}
