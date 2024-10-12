package org.example.o2o.api.dto.order.request;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Builder;

public record OrdersRequestDto(
	Long storeId,
	Integer page,
	Integer size,
	String status,
	String startDate,
	String endDate,
	String sortField,
	Sort.Direction sortDirection
) {
	@Builder
	public OrdersRequestDto {
		if (Objects.isNull(page)) {
			page = 0;
		}
		if (Objects.isNull(size)) {
			size = 10;
		}
		if (Objects.isNull(status)) {
			status = "";
		}
		if (Objects.isNull(startDate)) {
			startDate = "";
		}
		if (Objects.isNull(endDate)) {
			endDate = "";
		}
		if (Objects.isNull(sortField)) {
			sortField = "orderId";
		}
		if (Objects.isNull(sortDirection)) {
			sortDirection = Sort.Direction.DESC;
		}
	}

	public PageRequest toPageRequest() {
		return PageRequest.of(page(), size(), Sort.by(sortDirection(), sortField()));
	}
}
