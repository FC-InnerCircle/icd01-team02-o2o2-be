package org.example.o2o.api.dto.order.request;

public record OrderMenuCreateRequestDto(Long menuId, String menuName, Integer menuCount, Integer menuPrice,
										OrderOptionGroupCreateRequestDto[] optionGroups) {
}
