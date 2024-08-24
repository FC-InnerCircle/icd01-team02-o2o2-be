package org.example.o2o.domain.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {

	PENDING("미승인"),
	ACTIVE("활성화"),
	LOCKED("잠김"),
	DELETED("삭제");

	private final String statusName;

}
