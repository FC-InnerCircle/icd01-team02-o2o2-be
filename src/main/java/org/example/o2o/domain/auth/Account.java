package org.example.o2o.domain.auth;

import jakarta.persistence.Entity;
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
	private AccountRole role;

	@Builder
	public Account(String accountId, String password, String name, String contactNumber, String email,
		AccountRole role) {
		this.accountId = accountId;
		this.password = password;
		this.name = name;
		this.contactNumber = contactNumber;
		this.email = email;
		this.role = role;
	}
}
