package org.example.o2o.api.v2.service.menu;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.o2o.api.v2.dto.menu.response.MenuDetailResponseDto;
import org.example.o2o.api.v2.dto.menu.response.MenusResponseDto;
import org.example.o2o.common.component.file.FileManager;
import org.example.o2o.common.dto.file.FileDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.menu.MenuErrorCode;
import org.example.o2o.config.exception.enums.store.StoreErrorCode;
import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.file.FileGroupType;
import org.example.o2o.domain.menu.StoreMenu;
import org.example.o2o.domain.menu.StoreMenuStatus;
import org.example.o2o.domain.store.Store;
import org.example.o2o.repository.menu.StoreMenuRepository;
import org.example.o2o.repository.store.StoreRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuServiceV2 {

	private final StoreRepository storeRepository;
	private final StoreMenuRepository menuRepository;
	private final FileManager fileManager;

	/**
	 * 특정 가게의 모든 메뉴 조회
	 */
	@Transactional(readOnly = true)
	public MenusResponseDto findStoreMenus(final Long storeId, final PageRequest page,
		final List<StoreMenuStatus> status) {

		if (!storeRepository.existsById(storeId)) {
			throw new ApiException(StoreErrorCode.NOT_EXISTS_STORE);
		}

		return MenusResponseDto.of(menuRepository.findByStoreIdAndStatusIn(storeId, status, page));
	}

	/**
	 * 메뉴 상세 조회
	 */
	@Transactional(readOnly = true)
	public MenuDetailResponseDto findStoreMenuDetail(final Long menuId) {
		StoreMenu menu = menuRepository.findStoreMenuWithDetails(menuId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU));

		return MenuDetailResponseDto.of(menu);
	}

	/**
	 * 메뉴 단일 등록
	 */
	@Transactional
	public MenuDetailResponseDto register(
		final Long storeId, final List<MultipartFile> imageFiles, final StoreMenu menu) {
		List<FileDto.UploadFileResponse> uploadFileResponses = fileManager.storeFiles(imageFiles);

		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new ApiException(StoreErrorCode.NOT_EXISTS_STORE));

		// 업로드 파일이 존재하는 경우 썸네일 파일 등록
		if (!ObjectUtils.isEmpty(uploadFileResponses)) {
			FileGroup fileGroup = FileGroup.createFileGroup(FileGroupType.STORE);

			AtomicInteger ordering = new AtomicInteger(1);
			uploadFileResponses
				.forEach(uploadFile -> {
					FileDetail fileDetail = uploadFile.toFileDetail(ordering.getAndIncrement());
					fileGroup.addDetail(fileDetail);
				});
			menu.setImageFileGroup(fileGroup);
		}

		menu.setStore(store);
		return MenuDetailResponseDto.of(menuRepository.save(menu));
	}

	/**
	 * 메뉴 단일 삭제
	 */
	@Transactional
	public void delete(final Long menuId) {
		StoreMenu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new ApiException(MenuErrorCode.NOTFOUND_MENU));

		menu.disable();
	}
}
