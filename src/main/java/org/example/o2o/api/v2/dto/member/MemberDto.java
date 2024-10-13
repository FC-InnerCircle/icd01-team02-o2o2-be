package org.example.o2o.api.v2.dto.member;

import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.AddressStatus;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.member.MemberStatus;

import io.swagger.v3.oas.annotations.media.Schema;
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
		@Schema(description = "위도", example = "37.5453458368972")
		private Double latitude;

		@Schema(description = "경도", example = "127.18484365063364")
		private Double longitude;

		@Schema(description = "주소", example = "서울 금천구 벚꽃로 309")
		private String address;

		@Schema(description = "상세주소", example = "1층 101호")
		private String addressDetail;

		@Schema(description = "우편번호", example = "12345")
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
		@Schema(description = "주소 아이디", example = "1")
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
		@Schema(description = "주소 아이디", example = "1")
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
		@Schema(description = "회원 아이디", example = "1")
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
		@Schema(description = "닉넨임", example = "홍길동")
		private String nickName;

		@Schema(description = "연락처", example = "01011112222")
		private String contact;

		@Schema(description = "주소 정보")
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
		@Schema(description = "회원 아이디", example = "1")
		private Long memberId;

		public static MemberRegisterResponse of(Long memberId) {
			return new MemberRegisterResponse(memberId);
		}
	}

}
