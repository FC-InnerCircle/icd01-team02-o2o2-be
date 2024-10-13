package org.example.o2o.api.v1.controller.store;

import java.util.List;

import org.example.o2o.api.v1.docs.store.StoreDocsControllerV1;
import org.example.o2o.api.v1.dto.store.StoreDto.StoreSaveRequest;
import org.example.o2o.api.v1.dto.store.StoreDto.StoreSaveResponse;
import org.example.o2o.api.v1.dto.store.request.StoreListSearchRequest;
import org.example.o2o.api.v1.dto.store.response.StoreDetailResponse;
import org.example.o2o.api.v1.dto.store.response.StoreListSearchResponse;
import org.example.o2o.api.v1.service.store.StoreServiceV1;
import org.example.o2o.common.dto.ApiResponse;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreControllerV1 implements StoreDocsControllerV1 {

	private final StoreServiceV1 storeService;

	@Autowired
	public StoreControllerV1(StoreServiceV1 storeService) {
		this.storeService = storeService;
	}

	@GetMapping
	public ApiResponse<StoreListSearchResponse> findStores(StoreListSearchRequest requestDto) {
		Pageable pageable = PageRequest.of(
			requestDto.page(),
			requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField())
		);
		StoreListSearchResponse storeList = storeService.findStores(pageable);
		return ApiResponse.success(storeList);
	}

	@GetMapping("/{id}")
	public ApiResponse<StoreDetailResponse> findStoreById(@PathVariable Long id) {
		StoreDetailResponse storeDto = storeService.findStoreById(id);
		return ApiResponse.success(storeDto);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> deleteStoreById(@PathVariable Long id) {
		storeService.deleteStoreById(id);
		return ApiResponse.success(null);
	}

	@AdminAuthorize
	@PostMapping("")
	public ApiResponse<StoreSaveResponse> saveStore(
		@RequestPart("storeImageFiles") List<MultipartFile> imageFiles,
		@Valid @RequestPart("storeInfo") StoreSaveRequest saveRequest) {
		StoreSaveResponse saveResponse = storeService.saveStore(saveRequest, imageFiles);
		return ApiResponse.success(saveResponse);
	}
}
