package org.example.o2o.config.security;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetailsAuthority implements GrantedAuthority {

	private final String authority;

	public CustomUserDetailsAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
