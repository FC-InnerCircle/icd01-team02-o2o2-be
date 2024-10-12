package org.example.o2o.api.v1.dto.store.request;

import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record StoreListSearchRequest(
	@Schema(description = "페이지 번호 (0부터 시작)", example = "0")
	@Min(value = 0, message = "페이지 번호는 0 이상의 값이어야 합니다.")
	int page,

	@Schema(description = "페이지 당 항목 수", example = "10")
	@Min(value = 0, message = "페이지 당 항목 수는 0 이상의 값이어야 합니다.")
	int size,

	@Schema(description = "정렬 기준 필드", example = "createdAt")
	@Pattern(regexp = "^(createdAt)?$", message = "정렬 기준 필드는 'createdAt'만 허용됩니다.")
	String sortField,

	@Schema(description = "정렬 방향 (asc 또는 desc)", example = "desc")
	@Pattern(regexp = "^(asc|desc)?$", message = "정렬 방향은 'asc' 또는 'desc'만 허용됩니다.")
	Sort.Direction sortDirection
) {
	// 기본값 설정을 위해 추가 생성자 정의
	public StoreListSearchRequest() {
		this(0, 10, "createdAt", Sort.Direction.DESC);
	}
}
