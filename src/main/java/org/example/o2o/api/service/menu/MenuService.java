package org.example.o2o.api.service.menu;

import static org.example.o2o.api.dto.menu.MenuDto.*;

import java.util.List;

import org.example.o2o.api.dto.menu.MenuDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.menu.MenuErrorCode;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService {

	private final StoreMenuRepository menuRepository;

	/**
	 * 특정 가게의 모든 메뉴 조회
	 */
	public StoreMenusResponse findStoreMenu(final Long storeId, final PageRequest page,
		final List<StoreMenuStatus> status) {
		return StoreMenusResponse.of(menuRepository.findByStoreIdAndStatusIn(storeId, status, page));
	}

	/**
	 * 메뉴 상세 조회
	 */
	public StoreMenuDetailResponse findStoreMenuDetail(final Long storeId, final Long menuId) {
		StoreMenu menu = menuRepository.findByIdAndStoreId(storeId, menuId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU));

		return MenuDto.StoreMenuDetailResponse.of(menu);
	}
}
