package org.example.o2o.api.dto.menu.request;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import lombok.Builder;

public record MenusRequestDto(
	Integer page,
	Integer size,
	String status,
	String sortField,
	Sort.Direction sortDirection
) {

	@Builder
	public MenusRequestDto {
		if (Objects.isNull(page)) {
			page = 0;
		}
		if (Objects.isNull(size)) {
			size = 10;
		}
		if (Objects.isNull(status)) {
			status = "";
		}
		if (Objects.isNull(sortField)) {
			sortField = "ordering";
		}
		if (Objects.isNull(sortDirection)) {
			sortDirection = Sort.Direction.DESC;
		}
	}

	public PageRequest toPageRequest() {
		return PageRequest.of(page(), size(), Sort.by(sortDirection(), sortField()));
	}
}
