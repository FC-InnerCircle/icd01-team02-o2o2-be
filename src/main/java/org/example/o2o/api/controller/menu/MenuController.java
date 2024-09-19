package org.example.o2o.api.controller.menu;

import org.example.o2o.api.docs.menu.MenuDocsController;
import org.example.o2o.api.dto.menu.request.MenuCreateRequestDto;
import org.example.o2o.api.dto.menu.request.MenusRequestDto;
import org.example.o2o.api.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.dto.menu.response.MenusResponseDto;
import org.example.o2o.api.service.menu.MenuService;
import org.example.o2o.common.dto.ApiResponse;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MenuController implements MenuDocsController {

	private final MenuService menuService;

	@GetMapping("/stores/{storeId}/menus")
	public ApiResponse<MenusResponseDto> getStoreMenus(
		@PathVariable(name = "storeId") Long storeId, MenusRequestDto requestDto
	) {

		MenusResponseDto menusDto = menuService.findStoreMenus(
			storeId,
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		return ApiResponse.success(menusDto);
	}

	@GetMapping("/menus/{menuId}")
	public ApiResponse<MenuDetailResponseDto> getStoreMenuDetail(
		@PathVariable(name = "menuId") Long menuId) {

		return ApiResponse.success(menuService.findStoreMenuDetail(menuId));
	}

	@PostMapping("/stores/{storeId}/menus")
	public ApiResponse<MenuDetailResponseDto> registerMenu(
		@PathVariable(name = "storeId") Long storeId,
		@RequestBody @Valid MenuCreateRequestDto requestDto) {

		return ApiResponse.success(menuService.register(storeId, requestDto.toStoreMenu()));
	}

	@DeleteMapping("/menus/{menuId}")
	public ApiResponse<Void> deleteMenu(@PathVariable(name = "menuId") Long menuId) {

		menuService.delete(menuId);

		return ApiResponse.success();
	}
}
