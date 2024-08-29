package org.example.o2o.domain.auth;

import org.example.o2o.domain.auth.AccountCommand.ModifyProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

	public boolean isActive() {
		return status == AccountStatus.ACTIVE;
	}

	public void updateStatus(AccountStatus accountStatus) {
		this.status = accountStatus;
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void clearRefreshToken() {
		this.refreshToken = null;
	}

	public void updateProfileInfo(ModifyProfile modifyProfile) {
		this.name = modifyProfile.getName();
		this.contactNumber = modifyProfile.getContactNumber();
		this.email = modifyProfile.getEmail();
		this.password = modifyProfile.getPassword();
	}

}
