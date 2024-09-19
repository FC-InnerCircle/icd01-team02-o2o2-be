package org.example.o2o.fixture.member;

import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.AddressStatus;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.member.MemberStatus;

public class MemberFixture {

	public static Member createMember() {
		Member member = Member.builder()
			.name("홍길동")
			.contact("01012345678")
			.status(MemberStatus.ACTIVE)
			.build();

		member.addAddress(
			Address.builder()
				.latitude(37.5665)
				.longitude(126.9780)
				.address("서울시 블라")
				.detailAddress("몇동 몇호")
				.zipCode("12345")
				.status(AddressStatus.ACTIVE)
				.build()
		);

		return member;
	}
}
