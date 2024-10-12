package org.example.o2o.api.v2.controller.menu;

import org.example.o2o.api.v2.docs.menu.MenuOptionDocsControllerV2;
import org.example.o2o.api.v2.dto.menu.request.MenuOptionGroupCreateRequestDto;
import org.example.o2o.api.v2.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.api.v2.service.menu.MenuOptionServiceV2;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class MenuOptionControllerV2 implements MenuOptionDocsControllerV2 {

	private final MenuOptionServiceV2 menuOptionServiceV2;

	@PostMapping("/menus/{menuId}/options")
	public ApiResponse<MenuOptionGroupResponseDto> registerMenuOption(@PathVariable(name = "menuId") Long menuId,
		@RequestBody MenuOptionGroupCreateRequestDto requestDto) {

		return ApiResponse.success(menuOptionServiceV2.register(menuId, requestDto.toStoreMenuOptionGroup()));
	}

	@DeleteMapping("/options/{optionGroupId}")
	public ApiResponse<Void> deleteMenuOption(
		@PathVariable(name = "optionGroupId") Long optionGroupId) {

		menuOptionServiceV2.delete(optionGroupId);

		return ApiResponse.success();
	}

	@PatchMapping("/options/{optionGroupId}")
	public ApiResponse<MenuOptionGroupResponseDto> updateMenuOption(
		@PathVariable(name = "optionGroupId") Long optionGroupId,
		@RequestBody MenuOptionGroupCreateRequestDto requestDto) {

		return ApiResponse.success(menuOptionServiceV2.update(optionGroupId, requestDto.toStoreMenuOptionGroup()));
	}
}
