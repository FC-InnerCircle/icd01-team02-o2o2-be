package org.example.o2o.api.service.member;

import static org.assertj.core.api.Assertions.*;

import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRegisterRequest;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRegisterResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRemoveResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberRegisterRequest;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberRegisterResponse;
import org.example.o2o.api.v1.service.member.MemberService;
import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.AddressStatus;
import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.member.MemberStatus;
import org.example.o2o.fixture.member.MemberFixture;
import org.example.o2o.repository.member.AddressRepository;
import org.example.o2o.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	void setUp() {
		memberRepository.deleteAll();
		addressRepository.deleteAll();
	}

	@DisplayName("회원 가입 - 성공")
	@Test
	void registerMember_success() throws JsonProcessingException {
		// Given
		String memberJsonStr = """
				{
					"nickName": "상점",
					"contact": "01012345678",
					"address": {
						"latitude": 37.5665,
						"longitude": 126.9780,
						"address": "서울시 블라",
						"addressDetail": "몇동 몇호",
						"zipCode": "12345"
					}
				}
			""";

		MemberRegisterRequest memberRegisterRequest = objectMapper.readValue(memberJsonStr,
			MemberRegisterRequest.class);

		// When
		MemberRegisterResponse memberRegisterResponse = memberService.registerMember(memberRegisterRequest);

		// Then
		assertThat(memberRegisterResponse).isNotNull();
		assertThat(memberRegisterResponse.getMemberId()).isNotNull();
	}

	@DisplayName("회원 탈퇴 - 성공")
	@Test
	void withdrawalMember_success() {
		// Given
		Member savedMember = memberRepository.save(MemberFixture.createMember());

		// When
		memberService.withdrawalMember(savedMember.getId());

		// Then
		Member findMember = memberRepository.findById(savedMember.getId()).orElse(null);

		assertThat(findMember).isNotNull();
		assertThat(findMember.getStatus()).isEqualTo(MemberStatus.WITHDRAWAL);
	}

	@DisplayName("회원 주소 등록 - 성공")
	@Test
	void registerMemberAddress_success() throws JsonProcessingException {
		// Given
		Member savedMember = memberRepository.save(MemberFixture.createMember());
		Long memberId = savedMember.getId();

		String addressJsonStr = """
				{
					"latitude": 37.5665,
					"longitude": 126.9780,
					"address": "서울시 블라",
					"addressDetail": "몇동 몇호",
					"zipCode": "12345"
				}
			""";

		MemberAddressRegisterRequest memberAddressRegisterRequest = objectMapper.readValue(addressJsonStr,
			MemberAddressRegisterRequest.class);

		// When
		MemberAddressRegisterResponse memberAddressRegisterResponse = memberService.registerMemberAddress(
			memberId,
			memberAddressRegisterRequest);

		// Then
		Address findAddress = addressRepository.findById(memberAddressRegisterResponse.getAddressId()).orElse(null);
		assertThat(findAddress).isNotNull();
	}

	@DisplayName("회원 주소 삭제 - 성공")
	@Test
	void removeMemberAddress_success() throws JsonProcessingException {
		// Given
		Member savedMember = memberRepository.save(MemberFixture.createMember());
		Long memberId = savedMember.getId();

		Member member = memberRepository.findMemberWithActiveAddressById(memberId).orElse(null);
		assertThat(member).isNotNull();
		assertThat(member.getAddresses()).isNotEmpty();
		Long addressId = member.getAddresses().get(0).getId();

		// When
		MemberAddressRemoveResponse memberAddressRemoveResponse = memberService.removeMemberAddress(memberId,
			addressId);

		// Then
		Address findAddress = addressRepository.findById(memberAddressRemoveResponse.getAddressId()).orElse(null);
		assertThat(findAddress).isNotNull();
		assertThat(findAddress.getStatus()).isEqualTo(AddressStatus.DELETED);
	}
}
