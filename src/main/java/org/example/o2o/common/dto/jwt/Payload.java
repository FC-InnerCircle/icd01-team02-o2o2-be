package org.example.o2o.common.dto.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Payload {
	private String subject;

	private Long id;
	private String accountId;
	private String role;
	private String name;
}
