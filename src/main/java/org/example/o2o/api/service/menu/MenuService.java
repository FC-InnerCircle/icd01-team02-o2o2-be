package org.example.o2o.api.service.menu;

import java.util.List;

import org.example.o2o.api.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.dto.menu.response.MenusResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.menu.MenuErrorCode;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.domain.store.Store;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

	private final StoreRepository storeRepository;
	private final StoreMenuRepository menuRepository;

	/**
	 * 특정 가게의 모든 메뉴 조회
	 */
	@Transactional(readOnly = true)
	public MenusResponseDto findStoreMenus(final Long storeId, final PageRequest page,
		final List<StoreMenuStatus> status) {

		if (!storeRepository.existsById(storeId)) {
			throw new ApiException(StoreErrorCode.NOT_EXISTS_STORE);
		}

		return MenusResponseDto.of(menuRepository.findByStoreIdAndStatusIn(storeId, status, page));
	}

	/**
	 * 메뉴 상세 조회
	 */
	@Transactional(readOnly = true)
	public MenuDetailResponseDto findStoreMenuDetail(final Long menuId) {
		StoreMenu menu = menuRepository.findStoreMenuWithDetails(menuId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU));

		return MenuDetailResponseDto.of(menu);
	}

	/**
	 * 메뉴 단일 등록
	 */
	@Transactional
	public MenuDetailResponseDto register(final Long storeId, final StoreMenu menu) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));

		menu.setStore(store);
		return MenuDetailResponseDto.of(menuRepository.save(menu));
	}

	/**
	 * 메뉴 단일 삭제
	 */
	@Transactional
	public void delete(final Long menuId) {
		StoreMenu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU));

		menuRepository.delete(menu);
	}
}
