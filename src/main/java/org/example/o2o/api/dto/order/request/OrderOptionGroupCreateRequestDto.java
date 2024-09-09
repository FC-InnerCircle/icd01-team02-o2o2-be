package org.example.o2o.api.dto.order.request;

public record OrderOptionGroupCreateRequestDto(Long optionGroupId, String optionName,
											   OrderOptionCreateRequestDto[] options) {
}
