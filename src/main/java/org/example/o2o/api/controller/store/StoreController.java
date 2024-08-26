package org.example.o2o.api.controller.store;

import org.example.o2o.api.dto.store.StoreDetailResponseDto;
import org.example.o2o.api.dto.store.StoreListResponseDto;
import org.example.o2o.api.service.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;

	@Autowired
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}

	@GetMapping
	public ResponseEntity<StoreListResponseDto> getStores(Pageable pageable) {
		StoreListResponseDto storeList = storeService.getStores(pageable);
		return ResponseEntity.ok(storeList);
	}

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
