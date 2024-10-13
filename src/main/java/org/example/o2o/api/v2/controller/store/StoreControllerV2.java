package org.example.o2o.api.v2.controller.store;

import org.example.o2o.api.v2.docs.store.StoreDocsControllerV2;
import org.example.o2o.api.v2.dto.store.request.StoreListRequest;
import org.example.o2o.api.v2.dto.store.response.StoreDetailResponse;
import org.example.o2o.api.v2.dto.store.response.StoreListSearchResponse.StoreListResponse;
import org.example.o2o.api.v2.service.store.StoreServiceV2;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/stores")
public class StoreControllerV2 implements StoreDocsControllerV2 {

	private final StoreServiceV2 storeService;

	@GetMapping("")
	public ApiResponse<StoreListResponse> findStoreByIds(@RequestBody StoreListRequest request) {
		StoreListResponse storeListResponse = storeService.findStoreByIds(request.getStoreIds());
		return ApiResponse.success(storeListResponse);
	}

	@GetMapping("/{id}")
	public ApiResponse<StoreDetailResponse> getStoreById(@PathVariable Long id) {
		StoreDetailResponse storeDto = storeService.findStoreById(id);
		return ApiResponse.success(storeDto);
	}

}
