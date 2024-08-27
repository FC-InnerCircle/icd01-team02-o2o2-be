package org.example.o2o.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountCommand {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ModifyProfile {
		private String name;
		private String password;
		private String contactNumber;
		private String email;
	}
}
