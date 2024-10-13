package org.example.o2o.api.v2.service.review;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.example.o2o.api.v2.dto.review.request.ReviewRegisterRequest;
import org.example.o2o.api.v2.dto.review.response.ReviewRegisterResponse;
import org.example.o2o.common.component.file.CloudFileManager;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.order.OrderErrorCode;
import org.example.o2o.config.exception.enums.review.ReviewErrorCode;
import org.example.o2o.domain.file.FileDetail;
import org.example.o2o.domain.file.FileGroup;
import org.example.o2o.domain.file.FileGroupType;
import org.example.o2o.domain.file.FileTemporaryStorage;
import org.example.o2o.domain.file.FileTemporaryStorage.FileSyncStatus;
import org.example.o2o.domain.order.OrderInfo;
import org.example.o2o.domain.review.Review;
import org.example.o2o.repository.file.FileTemporaryStorageRepository;
import org.example.o2o.repository.member.MemberRepository;
import org.example.o2o.repository.order.OrderInfoRepository;
import org.example.o2o.repository.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceV2 {

	private final ReviewRepository reviewRepository;
	private final FileTemporaryStorageRepository fileTemporaryStorageRepository;
	private final MemberRepository memberRepository;
	private final OrderInfoRepository orderInfoRepository;
	private final CloudFileManager cloudFileManager;

	/**
	 * 리뷰 등록
	 * @param request 리뷰 등록 정보
	 * @return 등록된 리뷰 ID
	 */
	@Transactional
	public ReviewRegisterResponse registerReview(ReviewRegisterRequest request) {

		// 주문 정보 조회
		OrderInfo orderInfo = orderInfoRepository.findById(request.getOrderId())
			.orElseThrow(() -> new ApiException(OrderErrorCode.INVALID_ORDER));
		if (!orderInfo.isBuyer(request.getMemberId())) {
			throw new ApiException(OrderErrorCode.INVALID_ORDER);
		}
		if (reviewRepository.existsByOrder(orderInfo)) {
			throw new ApiException(ReviewErrorCode.DEUPLICATED_REVIEW);
		}

		Review savedReview = reviewRepository.save(request.toReview(orderInfo));

		// 이미지 정보(Pre-Signed URL)가 있는 경우 파일 정보 등록
		List<String> reviewImages = request.getReviewImage();
		if (!ObjectUtils.isEmpty(reviewImages)) {
			// Pre-Signed URL 파일 정보 조회
			List<FileTemporaryStorage> fileTempList = fileTemporaryStorageRepository.findBySyncStatusAndFullPathIn(
					FileSyncStatus.WAITING, reviewImages).stream()
				.filter(fileTemp -> cloudFileManager.isExistsFile(fileTemp.getStoredFileName()))
				.toList();

			// 파일 목록이 존재하는 경우 파일 그룹 및 파일 상세 저장
			if (!ObjectUtils.isEmpty(fileTempList)) {
				// FileGroup 생성
				FileGroup fileGroup = FileGroup.createFileGroup(FileGroupType.REVIEW);

				// FileDetail 설정
				AtomicInteger ordering = new AtomicInteger(1);
				fileTempList
					.forEach(fileTemp -> {
						FileDetail fileDetail = fileTemp.toFileDetail(ordering.getAndIncrement());
						fileGroup.addDetail(fileDetail);
					});

				// 리뷰 이미지 파일 설정
				savedReview.registerReviewIamgeFile(fileGroup);

				// 파일 정보 저장 완료 처리
				fileTempList.forEach(FileTemporaryStorage::completeFileSync);
			}
		}

		return ReviewRegisterResponse.of(savedReview.getId());
	}
}
