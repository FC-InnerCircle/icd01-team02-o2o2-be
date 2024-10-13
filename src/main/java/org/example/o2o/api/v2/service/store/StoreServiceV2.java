package org.example.o2o.api.v2.service.store;

import java.util.Collections;
import java.util.List;

import org.example.o2o.api.v2.dto.store.response.StoreDetailResponse;
import org.example.o2o.api.v2.dto.store.response.StoreListSearchResponse.StoreListResponse;
import org.example.o2o.api.v2.dto.store.response.StoreMenuResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.menu.MenuErrorCode;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.store.Store;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceV2 {

	private final StoreRepository storeRepository;
	private final StoreMenuRepository storeMenuRepository;

	/**
	 * 상점 목록 조회
	 * @param ids 상점 ID 목록
	 */
	public StoreListResponse findStoreByIds(List<Long> ids) {
		if (ObjectUtils.isEmpty(ids)) {
			return StoreListResponse.of(Collections.emptyList());
		}

		List<Store> stores = storeRepository.findStoreWithThumbnailAndByIds(ids);
		return StoreListResponse.of(stores);
	}

	/**
	 * 상점 단건 조회
	 * @param id 상점 ID
	 * @return
	 */
	public StoreDetailResponse findStoreById(Long id) {
		Store store = storeRepository.findStoreWithThumbnailAndById(id)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));

		List<StoreMenuResponse> storeMenus = storeMenuRepository.findActiveMenusByStoreId(id).stream()
			.map(storeMenu -> {
				return StoreMenuResponse.of(
					storeMenuRepository.findStoreMenuWithDetails(storeMenu.getId())
						.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU))
				);
			})
			.toList();

		return StoreDetailResponse.of(store, storeMenus);
	}
}
