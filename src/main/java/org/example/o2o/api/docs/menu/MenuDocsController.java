package org.example.o2o.api.docs.menu;

import java.util.List;

import org.example.o2o.api.dto.menu.request.MenuCreateRequestDto;
import org.example.o2o.api.dto.menu.request.MenusRequestDto;
import org.example.o2o.api.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.dto.menu.response.MenusResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface MenuDocsController {

	@Operation(summary = "메뉴 목록 조회", description = "store id에 해당하는 메뉴 목록을 반환합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴 목록 반환")
	@ApiResponse(responseCode = "400", description = "음식점을 찾을 수 없습니다.")
	org.example.o2o.common.dto.ApiResponse<MenusResponseDto> getStoreMenus(
		@PathVariable(name = "storeId") Long storeId, MenusRequestDto requestDto
	);

	@Operation(summary = "메뉴 상세정보 조회", description = "menu id에 해당하는 메뉴 상세정보를 반환합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴 상세정보 반환")
	@ApiResponse(responseCode = "404", description = "메뉴를 조회할 수 없습니다.")
	org.example.o2o.common.dto.ApiResponse<MenuDetailResponseDto> getStoreMenuDetail(
		@PathVariable(name = "menuId") Long menuId);

	@Operation(summary = "메뉴 등록", description = "store id에 해당하는 가게에 메뉴를 등록합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴를 성공적으로 저장 후 저장 된 값을 반환합니다.")
	@ApiResponse(responseCode = "400", description = "음식점을 찾을 수 없습니다.")
	org.example.o2o.common.dto.ApiResponse<MenuDetailResponseDto> registerMenu(
		@PathVariable(name = "storeId") Long storeId,
		@RequestPart("menuImageFiles") List<MultipartFile> imageFiles,
		@RequestBody @Valid MenuCreateRequestDto requestDto);

	@Operation(summary = "메뉴 삭제", description = "menu id에 해당하는 메뉴를 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴를 성공적으로 삭제합니다.")
	@ApiResponse(responseCode = "404", description = "메뉴를 조회할 수 없습니다.")
	org.example.o2o.common.dto.ApiResponse<Void> deleteMenu(@PathVariable(name = "menuId") Long menuId);
}
