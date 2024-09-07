package org.example.o2o.api.docs.menu;

import org.example.o2o.api.dto.menu.request.MenuOptionGroupCreateDto;
import org.example.o2o.api.dto.menu.response.MenuOptionGroupResponseDto;
import org.example.o2o.config.exception.ErrorResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "메뉴 옵션 API", description = "메뉴 옵션 추가/수정/삭제 API")
public interface MenuOptionDocsController {

	@Operation(summary = "메뉴 옵션 추가", description = "메뉴 옵션을 추가합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴 옵션 추가 후 메뉴 옵션 정보 반환")
	@ApiResponse(responseCode = "404", description = "메뉴 정보가 없는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<MenuOptionGroupResponseDto> registerMenuOption(
		@PathVariable(name = "menuId") Long menuId, @RequestBody MenuOptionGroupCreateDto requestDto);

	@Operation(summary = "메뉴 옵션 삭제", description = "메뉴 옵션을 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴 정상 삭제")
	@ApiResponse(responseCode = "404", description = "메뉴 옵션 정보가 없는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<Void> deleteMenuOption(
		@PathVariable(name = "optionGroupId") Long optionGroupId);

	@Operation(summary = "메뉴 옵션 수정", description = "메뉴 옵션을 수정합니다.")
	@ApiResponse(responseCode = "200", description = "메뉴 정상 수정")
	@ApiResponse(responseCode = "404", description = "메뉴 옵션 정보가 없는 경우",
		content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	org.example.o2o.common.dto.ApiResponse<MenuOptionGroupResponseDto> updateMenuOption(
		@PathVariable(name = "optionGroupId") Long optionGroupId, MenuOptionGroupCreateDto menuOptionGroupCreateDto);
}
