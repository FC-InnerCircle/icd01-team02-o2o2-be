package org.example.o2o.api.controller.menu;

import org.example.o2o.api.docs.menu.MenuOptionDocsController;
import org.example.o2o.api.dto.menu.request.MenuOptionGroupCreateDto;
import org.example.o2o.api.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.api.service.menu.MenuOptionService;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MenuOptionController implements MenuOptionDocsController {

	private final MenuOptionService menuOptionService;

	@PostMapping("/menus/{menuId}/options")
	public ApiResponse<MenuOptionGroupResponseDto> registerMenuOption(@PathVariable(name = "menuId") Long menuId,
		@RequestBody MenuOptionGroupCreateDto requestDto) {

		return ApiResponse.success(menuOptionService.register(menuId, requestDto.toStoreMenuOptionGroup()));
	}

	@DeleteMapping("/options/{optionGroupId}")
	public ApiResponse<Void> deleteMenuOption(
		@PathVariable(name = "optionGroupId") Long optionGroupId) {

		menuOptionService.delete(optionGroupId);

		return ApiResponse.success();
	}
}
