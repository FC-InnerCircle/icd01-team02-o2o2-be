package org.example.o2o.api.controller.store;

import java.util.List;

import org.example.o2o.api.dto.store.StoreDetailResponseDto;
import org.example.o2o.api.dto.store.StoreDto.StoreSaveRequest;
import org.example.o2o.api.dto.store.StoreDto.StoreSaveResponse;
import org.example.o2o.api.dto.store.StoreListRequestDto;
import org.example.o2o.api.dto.store.StoreListResponseDto;
import org.example.o2o.api.service.store.StoreService;
import org.example.o2o.config.security.annotation.AdminAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

	private final StoreService storeService;

	@Autowired
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}

	@Operation(summary = "스토어 목록 조회", description = "페이지네이션과 정렬을 사용하여 스토어 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어 목록을 반환합니다.")
	@GetMapping
	public org.example.o2o.common.dto.ApiResponse<StoreListResponseDto> getStores(StoreListRequestDto requestDto) {
		Pageable pageable = PageRequest.of(
			requestDto.page(),
			requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField())
		);
		StoreListResponseDto storeList = storeService.getStores(pageable);
		return org.example.o2o.common.dto.ApiResponse.success(storeList);
	}

	@Operation(summary = "스토어 상세 조회", description = "스토어 ID를 사용하여 특정 스토어의 상세 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어 상세 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "유효하지 않은 스토어 ID입니다.")
	@GetMapping("/{id}")
	public org.example.o2o.common.dto.ApiResponse<StoreDetailResponseDto> getStoreById(@PathVariable Long id) {
		StoreDetailResponseDto storeDto = storeService.getStoreById(id);
		return org.example.o2o.common.dto.ApiResponse.success(storeDto);
	}

	@Operation(summary = "스토어 삭제", description = "스토어 ID를 사용하여 스토어를 삭제합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어를 삭제했습니다.")
	@ApiResponse(responseCode = "404", description = "스토어를 찾을 수 없습니다.")
	@DeleteMapping("/{id}")
	public org.example.o2o.common.dto.ApiResponse<Void> deleteStoreById(@PathVariable Long id) {
		storeService.deleteStoreById(id);
		return org.example.o2o.common.dto.ApiResponse.success(null);
	}

	@AdminAuthorize
	@PostMapping("")
	public org.example.o2o.common.dto.ApiResponse<StoreSaveResponse> saveStore(
		@RequestPart("storeImageFiles") List<MultipartFile> imageFiles,
		@Valid @RequestPart("storeInfo") StoreSaveRequest saveRequest) {
		StoreSaveResponse saveResponse = storeService.saveStore(saveRequest, imageFiles);
		return org.example.o2o.common.dto.ApiResponse.success(saveResponse);
	}
}
