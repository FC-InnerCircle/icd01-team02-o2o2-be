package org.example.o2o.api.v1.controller.menu;

import java.util.List;

import org.example.o2o.api.v1.docs.menu.MenuDocsController;
import org.example.o2o.api.v1.dto.menu.request.MenuCreateRequestDto;
import org.example.o2o.api.v1.dto.menu.request.MenusRequestDto;
import org.example.o2o.api.v1.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.v1.dto.menu.response.MenusResponseDto;
import org.example.o2o.api.v1.service.menu.MenuService;
import org.example.o2o.common.dto.ApiResponse;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
		@RequestPart("menuImageFiles") List<MultipartFile> imageFiles,
		@RequestPart @Valid MenuCreateRequestDto requestDto) {

		return ApiResponse.success(menuService.register(storeId, imageFiles, requestDto.toStoreMenu()));
	}

	@DeleteMapping("/menus/{menuId}")
	public ApiResponse<Void> deleteMenu(@PathVariable(name = "menuId") Long menuId) {

		menuService.delete(menuId);

		return ApiResponse.success();
	}
}
