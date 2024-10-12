package org.example.o2o.api.v2.controller.menu;

import java.util.List;

import org.example.o2o.api.v2.docs.menu.MenuDocsControllerV2;
import org.example.o2o.api.v2.dto.menu.request.MenuCreateRequestDto;
import org.example.o2o.api.v2.dto.menu.request.MenusRequestDto;
import org.example.o2o.api.v2.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.v2.dto.menu.response.MenusResponseDto;
import org.example.o2o.api.v2.service.menu.MenuServiceV2;
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
@RequestMapping("/api/v2")
public class MenuControllerV2 implements MenuDocsControllerV2 {

	private final MenuServiceV2 menuServiceV2;

	@GetMapping("/stores/{storeId}/menus")
	public ApiResponse<MenusResponseDto> getStoreMenus(
		@PathVariable(name = "storeId") Long storeId, MenusRequestDto requestDto
	) {

		MenusResponseDto menusDto = menuServiceV2.findStoreMenus(
			storeId,
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		return ApiResponse.success(menusDto);
	}

	@GetMapping("/menus/{menuId}")
	public ApiResponse<MenuDetailResponseDto> getStoreMenuDetail(
		@PathVariable(name = "menuId") Long menuId) {

		return ApiResponse.success(menuServiceV2.findStoreMenuDetail(menuId));
	}

	@PostMapping("/stores/{storeId}/menus")
	public ApiResponse<MenuDetailResponseDto> registerMenu(
		@PathVariable(name = "storeId") Long storeId,
		@RequestPart("menuImageFiles") List<MultipartFile> imageFiles,
		@RequestPart @Valid MenuCreateRequestDto requestDto) {

		return ApiResponse.success(menuServiceV2.register(storeId, imageFiles, requestDto.toStoreMenu()));
	}

	@DeleteMapping("/menus/{menuId}")
	public ApiResponse<Void> deleteMenu(@PathVariable(name = "menuId") Long menuId) {

		menuServiceV2.delete(menuId);

		return ApiResponse.success();
	}
}
