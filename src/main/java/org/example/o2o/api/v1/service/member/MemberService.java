package org.example.o2o.api.v1.service.member;

import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRegisterRequest;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRegisterResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRemoveResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberRegisterRequest;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberRegisterResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberWithdrawalResponse;
import org.example.o2o.config.exception.ApiException;
import org.example.o2o.config.exception.enums.member.AddressErrorCode;
import org.example.o2o.config.exception.enums.member.MemberErrorCode;
import org.example.o2o.domain.member.Address;
import org.example.o2o.domain.member.AddressStatus;
import org.example.o2o.domain.member.Member;
import org.example.o2o.repository.member.AddressRepository;
import org.example.o2o.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;
	private final AddressRepository addressRepository;

	/**
	 * 회원 정보 등록
	 * @param request 회원 정보
	 * @return 등록된 회원 ID
	 */
	@Transactional
	public MemberRegisterResponse registerMember(MemberRegisterRequest request) {
		Member savedMember = memberRepository.save(request.toMember());

		return MemberRegisterResponse.of(savedMember.getId());
	}

	/**
	 * 회원 탈퇴
	 * @param memberId 회원 ID
	 * @return 탈퇴 회원 ID
	 */
	@Transactional
	public MemberWithdrawalResponse withdrawalMember(Long memberId) {
		Member findMember = memberRepository.findActiveMemberById(memberId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER));

		findMember.withdraw();

		return MemberWithdrawalResponse.of(memberId);
	}

	@Autowired
	private EntityManager entityManager;

	/**
	 * 회원 주소 등록
	 * @param memberId 회원 ID
	 * @param request 주소 정보
	 * @return 주소 ID
	 */
	@Transactional
	public MemberAddressRegisterResponse registerMemberAddress(Long memberId, MemberAddressRegisterRequest request) {
		Member findMember = memberRepository.findActiveMemberById(memberId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER));

		// 회원 정보에 새로운 주소를 추가하고 저장
		Address newAddress = request.toAddress();
		findMember.addAddress(newAddress);
		addressRepository.save(newAddress);

		return MemberAddressRegisterResponse.of(newAddress.getId());
	}

	/**
	 * 회원 주소 삭제
	 * @param memberId 회원 ID
	 * @param addressId 주소 ID
	 * @return 삭제된 주소 ID
	 */
	@Transactional
	public MemberAddressRemoveResponse removeMemberAddress(Long memberId, Long addressId) {
		Address findAddress = addressRepository.findAddressByAddressId(addressId)
			.orElseThrow(() -> new ApiException(AddressErrorCode.NOT_FOUND_ADDRESS));

		if (!memberId.equals(findAddress.getMember().getId())) {
			throw new ApiException(AddressErrorCode.INVALID_MEMBER_ID);
		}

		if (findAddress.getStatus() != AddressStatus.ACTIVE) {
			throw new ApiException(AddressErrorCode.NOT_ACTIVE_STATUS);
		}

		findAddress.deleteAddress();

		return MemberAddressRemoveResponse.of(addressId);
	}
}
