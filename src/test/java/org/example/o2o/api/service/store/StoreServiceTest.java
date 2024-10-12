package org.example.o2o.api.service.store;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import org.example.o2o.api.v1.dto.store.StoreDto.StoreSaveRequest;
import org.example.o2o.api.v1.dto.store.StoreDto.StoreSaveResponse;
import org.example.o2o.api.v1.dto.store.request.StoreListSearchRequest;
import org.example.o2o.api.v1.dto.store.response.StoreDetailSearchResponse;
import org.example.o2o.api.v1.dto.store.response.StoreListSearchResponse;
import org.example.o2o.api.v1.service.store.StoreService;
import org.example.o2o.common.component.file.FileManager;
import org.example.o2o.common.dto.file.FileDto;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.domain.file.ResourceLocation;
import org.example.o2o.domain.store.Store;
import org.example.o2o.fixture.store.StoreFixture;
import org.example.o2o.repository.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Transactional
public class StoreServiceTest {

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean(name = "localFileManager")
	FileManager fileManager;

	@BeforeEach
	void setUp() {
		storeRepository.deleteAll();
		storeRepository.save(StoreFixture.createStore());
	}

	@Test
	void testGetStoresSuccessful() {
		Store store = StoreFixture.createStore();
		storeRepository.save(store);

		StoreListSearchRequest requestDto = new StoreListSearchRequest(0, 10, "createdAt", Sort.Direction.DESC);
		Pageable pageable = PageRequest.of(requestDto.page(), requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField()));

		StoreListSearchResponse responseDto = storeService.getStores(pageable);

		assertThat(responseDto.stores()).isNotEmpty();
		assertThat(responseDto.stores().get(0).name()).isEqualTo("Test Store");
	}

	@Test
	void testGetStoresPageSizeOne() {
		storeRepository.save(StoreFixture.createStore());
		storeRepository.save(StoreFixture.createCustomStore("Store 2", "Korean"));

		StoreListSearchRequest requestDto = new StoreListSearchRequest(0, 1, "createdAt", Sort.Direction.DESC);
		Pageable pageable = PageRequest.of(requestDto.page(), requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField()));

		StoreListSearchResponse responseDto = storeService.getStores(pageable);

		assertThat(responseDto.stores()).hasSize(1);
	}

	@Test
	void testGetStoresSortByName() {
		storeRepository.save(StoreFixture.createCustomStore("B Store", "Korean"));
		storeRepository.save(StoreFixture.createCustomStore("A Store", "Korean"));

		StoreListSearchRequest requestDto = new StoreListSearchRequest(0, 10, "name", Sort.Direction.ASC);
		Pageable pageable = PageRequest.of(requestDto.page(), requestDto.size(),
			Sort.by(requestDto.sortDirection(), requestDto.sortField()));

		StoreListSearchResponse responseDto = storeService.getStores(pageable);

		assertThat(responseDto.stores().get(0).name()).isEqualTo("A Store");
	}

	@Test
	void testGetStoreByIdSuccessful() {
		Store store = storeRepository.save(StoreFixture.createStore());
		StoreDetailSearchResponse responseDto = storeService.getStoreById(store.getId());

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.name()).isEqualTo("Test Store");
	}

	@Test
	void testDeleteStoreByIdSuccessful() {
		Store store = storeRepository.save(StoreFixture.createStore());
		StoreDetailSearchResponse responseDto = storeService.getStoreById(store.getId());

		assertThat(responseDto).isNotNull();
		storeService.deleteStoreById(store.getId());

		assertThatThrownBy(() -> storeService.getStoreById(store.getId()))
			.isInstanceOf(ApiException.class);
	}

	@Test
	@DisplayName("상점 등록 - 성공")
	void saveStore_success() throws JsonProcessingException {
		// Given
		String storeJsonStr = """
				{
					"name": "상점",
					"contactNumber": "01012345678",
					"zipCode": "12345",
					"address": "서울 금천구 벚꽃로 309",
					"addressDetail": "상세주소",
					"latitude": "37.54",
					"longitude": "127.18",
					"openTime": "12:00",
					"closeTime": "23:00",
					"categories": ["CAFE", "CHICKEN"],
					"minimumOrderAmount": "10000"
				}
			""";

		StoreSaveRequest storeSaveRequest = objectMapper.readValue(storeJsonStr,
			StoreSaveRequest.class);

		MockMultipartFile thumbnailFile = new MockMultipartFile(
			"thumbnailFile",
			"thumbnail.jpg",
			"text/plain",
			"thumbnail file".getBytes(StandardCharsets.UTF_8));

		String storedFileName = "storedFile";
		String uploadPath = "upload";
		when(fileManager.storeFiles(anyList())).thenReturn(
			List.of(FileDto.UploadFileResponse.builder()
				.storeFileName(storedFileName)
				.originalFileName(thumbnailFile.getOriginalFilename())
				.path(uploadPath)
				.fileSize(thumbnailFile.getSize())
				.resourceLocation(ResourceLocation.LOCAL)
				.build())
		);

		// When
		StoreSaveResponse storeSaveResponse = storeService.saveStore(storeSaveRequest, List.of(thumbnailFile));

		// Then
		assertThat(storeSaveResponse).isNotNull();
		assertThat(storeSaveResponse.getName()).isEqualTo("상점");
		assertThat(storeSaveResponse.getContactNumber()).isEqualTo("01012345678");
		assertThat(storeSaveResponse.getZipCode()).isEqualTo("12345");

		assertThat(storeSaveResponse.getCategories()).isNotEmpty();
		assertThat(storeSaveResponse.getCategories().size()).isEqualTo(2);

		assertThat(storeSaveResponse.getImageUrls()).isNotEmpty();
		assertThat(storeSaveResponse.getImageUrls().get(0)).isEqualTo(Paths.get(uploadPath, storedFileName).toString());
	}

}
