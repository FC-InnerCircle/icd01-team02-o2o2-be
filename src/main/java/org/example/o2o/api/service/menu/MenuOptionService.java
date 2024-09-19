package org.example.o2o.api.service.menu;

import org.example.o2o.api.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.menu.MenuErrorCode;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuOptionGroup;
import org.example.o2o.repository.menu.StoreMenuOptionGroupRepository;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuOptionService {

	private final StoreMenuRepository menuRepository;

	private final StoreMenuOptionGroupRepository menuOptionGroupRepository;

	/**
	 * 메뉴 옵션 추가
	 */
	@Transactional
	public MenuOptionGroupResponseDto register(final Long menuId, final StoreMenuOptionGroup option) {
		StoreMenu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU));

		option.setMenu(menu);

		return MenuOptionGroupResponseDto.of(menuOptionGroupRepository.save(option));
	}

	@Transactional
	public void delete(final Long optionGroupId) {
		StoreMenuOptionGroup menuOptionGroup = menuOptionGroupRepository.findByIdAndIsDeletedFalse(optionGroupId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU_OPTION));

		menuOptionGroup.delete();
	}

	@Transactional
	public MenuOptionGroupResponseDto update(final Long optionGroupId, final StoreMenuOptionGroup afterOptionGroup) {
		StoreMenuOptionGroup menuOptionGroup = menuOptionGroupRepository.findByIdAndIsDeletedFalse(optionGroupId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU_OPTION));

		menuOptionGroup.updateBy(afterOptionGroup);

		return MenuOptionGroupResponseDto.of(menuOptionGroupRepository.save(menuOptionGroup));
	}
}
