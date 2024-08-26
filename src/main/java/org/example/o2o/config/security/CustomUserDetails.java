package org.example.o2o.config.security;

import java.util.Collection;
import java.util.List;

import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.auth.AccountErrorCode;
import org.example.o2o.domain.auth.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private final Account account;

	public CustomUserDetails(Account account) {
		this.account = account;
	}

	public Long getId() {
		return account.getId();
	}

	public String getAccountId() {
		return account.getAccountId();
	}

	public void validateId(Long id) {
		if (account.getId().compareTo(id) != 0) {
			throw new ApiException(AccountErrorCode.INVALID_ACCOUNT_INFO);
		}
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
