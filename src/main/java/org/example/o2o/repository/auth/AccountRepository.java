package org.example.o2o.repository.auth;

import java.util.Optional;

import org.example.o2o.domain.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByAccountId(String accountId);
}
