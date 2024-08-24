package org.example.o2o.api.service.menu;

import static org.example.o2o.api.dto.menu.MenuDto.*;

import java.util.List;

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
}
