package org.example.o2o.config.security;

import java.util.Collection;
import java.util.List;

import org.example.o2o.domain.auth.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private final Account account;

	public CustomUserDetails(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		CustomUserDetailsAuthority customUserDetailsAuthority = new CustomUserDetailsAuthority(
			account.getRole().name());
		return List.of(customUserDetailsAuthority);
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getAccountId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
