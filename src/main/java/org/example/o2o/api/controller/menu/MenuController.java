package org.example.o2o.api.controller.menu;

import org.example.o2o.api.dto.menu.request.MenuCreateRequestDto;
import org.example.o2o.api.dto.menu.request.MenusRequestDto;
import org.example.o2o.api.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.dto.menu.response.MenusResponseDto;
import org.example.o2o.api.service.menu.MenuService;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/stores/{storeId}/menus")
	public org.example.o2o.common.dto.ApiResponse<MenusResponseDto> getStoreMenus(
		@PathVariable(name = "storeId") Long storeId, MenusRequestDto requestDto
	) {

		MenusResponseDto menusDto = menuService.findStoreMenus(
			storeId,
			requestDto.toPageRequest(),
			StoreMenuStatus.getMenuStatuses(requestDto.status())
		);

		return org.example.o2o.common.dto.ApiResponse.success(menusDto);
	}

	@GetMapping("/menus/{menuId}")
	public org.example.o2o.common.dto.ApiResponse<MenuDetailResponseDto> getStoreMenuDetail(
		@PathVariable(name = "menuId") Long menuId) {

		return org.example.o2o.common.dto.ApiResponse.success(menuService.findStoreMenuDetail(menuId));
	}

	@Operation(summary = "메뉴 등록", description = "store id에 해당하는 가게에 메뉴를 등록합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴를 성공적으로 저장 후 저장 된 값을 반환합니다.")
	@ApiResponse(responseCode = "400", description = "유효하지 않은 스토어 ID입니다.")
	@PostMapping("/stores/{storeId}/menus")
	public org.example.o2o.common.dto.ApiResponse<MenuDetailResponseDto> registerMenu(
		@PathVariable(name = "storeId") Long storeId,
		@RequestBody @Valid MenuCreateRequestDto requestDto) {

		return org.example.o2o.common.dto.ApiResponse.success(menuService.register(storeId, requestDto.toStoreMenu()));
	}
}
