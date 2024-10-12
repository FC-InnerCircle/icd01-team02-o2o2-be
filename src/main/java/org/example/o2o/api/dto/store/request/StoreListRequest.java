package org.example.o2o.api.dto.store.request;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreListRequest {
	List<Long> storeIds;
}
