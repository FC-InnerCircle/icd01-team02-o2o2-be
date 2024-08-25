package org.example.o2o.api.controller.menu;

import org.example.o2o.api.dto.menu.MenuDto;
import org.example.o2o.api.service.menu.MenuService;
import org.example.o2o.common.dto.ApiResponse;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores/{storeId}/menus")
public class MenuController {

	private final MenuService menuService;

	@GetMapping
	public ApiResponse<MenuDto.StoreMenusResponse> getStoreMenus(
		@PathVariable(name = "storeId") Long storeId,
		@RequestParam(name = "status", required = false) String status,
		@RequestParam(name = "page", defaultValue = "0", required = false) int page,
		@RequestParam(name = "size", defaultValue = "10", required = false) int size
	) {

		MenuDto.StoreMenusResponse menusResponse = menuService.findStoreMenu(
			storeId,
			PageRequest.of(page, size, Sort.by("ordering").ascending()),
			StoreMenuStatus.getMenuStatuses(status)
		);

		return ApiResponse.success(menusResponse);
	}

	@GetMapping("/{menuId}")
	public ApiResponse<MenuDto.StoreMenuDetailResponse> getStoreMenuDetail(@PathVariable(name = "storeId") Long storeId,
		@PathVariable(name = "menuId") Long menuId) {

		return ApiResponse.success(menuService.findStoreMenuDetail(storeId, menuId));
	}
}
