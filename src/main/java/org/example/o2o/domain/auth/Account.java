package org.example.o2o.domain.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String accountId;
	private String password;
	private String name;
	private String contactNumber;
	private String email;

	@Enumerated(EnumType.STRING)
	private AccountRole role;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	private String refreshToken;

	@Builder
	public Account(String accountId, String password, String name, String contactNumber, String email, AccountRole role,
		AccountStatus status, String refreshToken) {
		this.accountId = accountId;
		this.password = password;
		this.name = name;
		this.contactNumber = contactNumber;
		this.email = email;
		this.role = role;
		this.status = status;
		this.refreshToken = refreshToken;
	}

	public boolean isActive() {
		return status == AccountStatus.ACTIVE;
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void clearRefreshToken() {
		this.refreshToken = null;
	}
}
