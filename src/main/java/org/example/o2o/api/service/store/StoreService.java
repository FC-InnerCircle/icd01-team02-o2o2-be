package org.example.o2o.api.service.store;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.o2o.api.dto.store.StoreDetailResponseDto;
import org.example.o2o.api.dto.store.StoreDto.StoreSaveRequest;
import org.example.o2o.api.dto.store.StoreDto.StoreSaveResponse;
import org.example.o2o.api.dto.store.StoreListResponseDto;
import org.example.o2o.common.component.file.FileManager;
import org.example.o2o.common.dto.file.FileDto.UploadFileResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.file.FileGroupType;
import org.example.o2o.domain.store.Store;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StoreService {

	private final StoreRepository storeRepository;
	private final FileManager fileManager;

	@Autowired
	public StoreService(StoreRepository storeRepository, @Qualifier("localFileManager") FileManager fileManager) {
		this.storeRepository = storeRepository;
		this.fileManager = fileManager;
	}

	public StoreListResponseDto getStores(Pageable pageable) {
		Page<Store> storePage = storeRepository.findAllByOrderByCreatedAtDesc(pageable);
		return StoreListResponseDto.of(storePage);
	}

	public StoreDetailResponseDto getStoreById(Long id) {
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));
		return StoreDetailResponseDto.of(store);
	}

	@Transactional
	public void deleteStoreById(Long id) {
		Store store = storeRepository.findById(id)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));
		storeRepository.delete(store);
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
			uploadFileResponses.stream()
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
