package org.example.o2o.repository.member;

import org.example.o2o.domain.member.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {
}
