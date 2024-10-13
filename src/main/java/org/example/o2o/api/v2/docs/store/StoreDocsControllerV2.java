package org.example.o2o.api.v2.docs.store;

import org.example.o2o.api.v2.dto.store.request.StoreListRequest;
import org.example.o2o.api.v2.dto.store.response.StoreDetailResponse;
import org.example.o2o.api.v2.dto.store.response.StoreListSearchResponse.StoreListResponse;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface StoreDocsControllerV2 {

	@Operation(summary = "스토어 목록 조회", description = "스토어 ID 목록을 통해 스토어 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어 목록을 반환합니다.")
	org.example.o2o.common.dto.ApiResponse<StoreListResponse> findStoreByIds(
		StoreListRequest request);

	@Operation(summary = "스토어 상세 조회", description = "스토어 ID를 사용하여 특정 스토어의 상세 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어 상세 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "유효하지 않은 스토어 ID입니다.")
	org.example.o2o.common.dto.ApiResponse<StoreDetailResponse> getStoreById(@PathVariable Long id);

}
