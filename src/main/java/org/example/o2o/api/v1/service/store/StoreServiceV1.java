package org.example.o2o.api.v1.service.store;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.o2o.api.v1.dto.store.StoreDto.StoreSaveRequest;
import org.example.o2o.api.v1.dto.store.StoreDto.StoreSaveResponse;
import org.example.o2o.api.v1.dto.store.response.StoreDetailResponse;
import org.example.o2o.api.v1.dto.store.response.StoreListSearchResponse;
import org.example.o2o.api.v2.dto.store.response.StoreListSearchResponse.StoreListResponse;
import org.example.o2o.common.component.file.FileManager;
import org.example.o2o.common.dto.file.FileDto.UploadFileResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.file.FileGroupType;
import org.example.o2o.domain.store.Store;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreServiceV1 {

	private final StoreRepository storeRepository;
	private final StoreMenuRepository storeMenuRepository;
	private final FileManager fileManager;

	/**
	 * 상점 목록 조회
	 * @param pageable 페이징 정보
	 * @return
	 */
	public StoreListSearchResponse findStores(Pageable pageable) {
		Page<Store> storePage = storeRepository.findAllByOrderByCreatedAtDesc(pageable);
		return StoreListSearchResponse.of(storePage);
	}

	/**
	 * 상점 목록 조회
	 * @param ids 상점 ID 목록
	 */
	public StoreListResponse findStoreByIds(List<Long> ids) {
		if (ObjectUtils.isEmpty(ids)) {
			return StoreListResponse.of(Collections.emptyList());
		}

		List<Store> stores = storeRepository.findStoreWithThumbnailAndByIds(ids);
		return StoreListResponse.of(stores);
	}

	/**
	 * 상점 단건 조회
	 * @param id 상점 ID
	 * @return
	 */
	public StoreDetailResponse findStoreById(Long id) {
		Store store = storeRepository.findStoreWithThumbnailAndById(id)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));

		return StoreDetailResponse.of(store);
	}

	/**
	 * 상점 정보 삭제 (Soft Delete)
	 * @param id
	 */
	@Transactional
	public void deleteStoreById(Long id) {
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));
		store.delete();
	}

	@Transactional
	public StoreSaveResponse saveStore(StoreSaveRequest request, List<MultipartFile> imageFiles) {
		Store savedStore = storeRepository.save(request.toStore());
		List<UploadFileResponse> uploadFileResponses = fileManager.storeFiles(imageFiles);

		// 업로드 파일이 존재하는 경우 썸네일 파일 등록
		if (!ObjectUtils.isEmpty(uploadFileResponses)) {
			// FileGroup 생성
			FileGroup fileGroup = FileGroup.createFileGroup(FileGroupType.STORE);

			// FileDetail 설정
			AtomicInteger ordering = new AtomicInteger(1);
			uploadFileResponses
				.forEach(uploadFile -> {
					FileDetail fileDetail = uploadFile.toFileDetail(ordering.getAndIncrement());
					fileGroup.addDetail(fileDetail);
				});

			// 음식점 썸네일 파일 등록
			savedStore.registerThumbnailFile(fileGroup);
		}

		return StoreSaveResponse.of(savedStore);
	}
}
