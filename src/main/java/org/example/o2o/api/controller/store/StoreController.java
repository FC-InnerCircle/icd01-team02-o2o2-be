package org.example.o2o.api.controller.store;

import org.example.o2o.api.dto.store.StoreDetailResponseDto;
import org.example.o2o.api.dto.store.StoreListRequestDto;
import org.example.o2o.api.dto.store.StoreListResponseDto;
import org.example.o2o.api.service.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
	public ResponseEntity<StoreListResponseDto> getStores(StoreListRequestDto requestDto) {
		Pageable pageable = PageRequest.of(
			requestDto.page(),
			requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField())
		);
		StoreListResponseDto storeList = storeService.getStores(pageable);
		return ResponseEntity.ok(storeList);
	}

	@Operation(summary = "스토어 상세 조회", description = "스토어 ID를 사용하여 특정 스토어의 상세 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 스토어 상세 정보를 반환합니다.")
	@ApiResponse(responseCode = "400", description = "유효하지 않은 스토어 ID입니다.")
	@GetMapping("/{id}")
	public ResponseEntity<StoreDetailResponseDto> getStoreById(@PathVariable Long id) {
		try {
			StoreDetailResponseDto storeDto = storeService.getStoreById(id);
			return ResponseEntity.ok(storeDto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
