package org.example.o2o.config.security;

import org.example.o2o.domain.auth.Account;
import org.example.o2o.repository.auth.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final AccountRepository accountRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account account = accountRepository.findByAccountId(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));

		return new CustomUserDetails(account);
	}
}
