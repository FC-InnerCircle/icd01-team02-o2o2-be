package org.example.o2o.api.dto.member;

import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.AddressStatus;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.member.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberAddressRegisterRequest {
		private Double latitude;
		private Double longitude;
		private String address;
		private String addressDetail;
		private String zipCode;

		public Address toAddress() {
			return Address.builder()
				.address(address)
				.detailAddress(addressDetail)
				.latitude(latitude)
				.longitude(longitude)
				.status(AddressStatus.ACTIVE)
				.build();
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberAddressRegisterResponse {
		private Long addressId;

		public static MemberAddressRegisterResponse of(Long addressId) {
			return new MemberAddressRegisterResponse(addressId);
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberAddressRemoveResponse {
		private Long addressId;

		public static MemberAddressRemoveResponse of(Long addressId) {
			return new MemberAddressRemoveResponse(addressId);
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberWithdrawalResponse {
		private Long memberId;

		public static MemberWithdrawalResponse of(Long memberId) {
			return new MemberWithdrawalResponse(memberId);
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberRegisterRequest {
		private String nickName;
		private String contact;
		private MemberAddressRegisterRequest address;

		public Member toMember() {
			Member member = Member.builder()
				.nickname(nickName)
				.contact(contact)
				.status(MemberStatus.ACTIVE)
				.build();

			member.addAddress(
				Address.builder()
					.longitude(address.longitude)
					.latitude(address.latitude)
					.address(address.address)
					.detailAddress(address.addressDetail)
					.zipCode(address.zipCode)
					.status(AddressStatus.ACTIVE)
					.build()
			);

			return member;
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MemberRegisterResponse {
		private Long memberId;

		public static MemberRegisterResponse of(Long memberId) {
			return new MemberRegisterResponse(memberId);
		}
	}

}
