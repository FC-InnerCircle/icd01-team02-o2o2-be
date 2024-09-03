package org.example.o2o.api.docs.store;

import java.util.List;

import org.example.o2o.api.dto.store.StoreDetailResponseDto;
import org.example.o2o.api.dto.store.StoreDto;
import org.example.o2o.api.dto.store.StoreListRequestDto;
import org.example.o2o.api.dto.store.StoreListResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface StoreDocsController {

	@Operation(summary = "스토어 목록 조회", description = "페이지네이션과 정렬을 사용하여 스토어 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어 목록을 반환합니다.")
	org.example.o2o.common.dto.ApiResponse<StoreListResponseDto> getStores(StoreListRequestDto requestDto);

	@Operation(summary = "스토어 상세 조회", description = "스토어 ID를 사용하여 특정 스토어의 상세 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어 상세 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "유효하지 않은 스토어 ID입니다.")
	org.example.o2o.common.dto.ApiResponse<StoreDetailResponseDto> getStoreById(@PathVariable Long id);

	@Operation(summary = "상점 정보 저장", description = "상점 정보를 저장합니다.")
	@ApiResponse(responseCode = "200", description = "상점 정보를 저장하고, 저장된 상점 정보를 반환합니다.")
	org.example.o2o.common.dto.ApiResponse<StoreDto.StoreSaveResponse> saveStore(
		@RequestPart("storeImageFiles") List<MultipartFile> imageFiles,
		@Valid @RequestPart("storeInfo") StoreDto.StoreSaveRequest saveRequest);

}
