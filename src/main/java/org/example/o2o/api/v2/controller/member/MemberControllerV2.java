package org.example.o2o.api.v2.controller.member;

import org.example.o2o.api.v2.docs.member.MemberDocsControllerV2;
import org.example.o2o.api.v2.dto.member.MemberDto.MemberAddressRegisterRequest;
import org.example.o2o.api.v2.dto.member.MemberDto.MemberAddressRegisterResponse;
import org.example.o2o.api.v2.dto.member.MemberDto.MemberAddressRemoveResponse;
import org.example.o2o.api.v2.dto.member.MemberDto.MemberRegisterRequest;
import org.example.o2o.api.v2.dto.member.MemberDto.MemberRegisterResponse;
import org.example.o2o.api.v2.dto.member.MemberDto.MemberWithdrawalResponse;
import org.example.o2o.api.v2.service.member.MemberServiceV2;
import org.example.o2o.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/members")
public class MemberControllerV2 implements MemberDocsControllerV2 {

	private final MemberServiceV2 memberService;

	@PostMapping("")
	public ApiResponse<MemberRegisterResponse> registerMember(
		@RequestBody @Valid MemberRegisterRequest request
	) {
		MemberRegisterResponse response = memberService.registerMember(request);
		return ApiResponse.success(response);
	}

	@DeleteMapping("/{memberId}")
	public ApiResponse<MemberWithdrawalResponse> withdrawalMember(
		@PathVariable(name = "memberId") Long memberId
	) {
		MemberWithdrawalResponse response = memberService.withdrawalMember(memberId);
		return ApiResponse.success(response);
	}

	@PostMapping("/{memberId}/address")
	public ApiResponse<MemberAddressRegisterResponse> registerMemberAddress(
		@PathVariable(name = "memberId") Long memberId,
		@RequestBody @Valid MemberAddressRegisterRequest request
	) {
		MemberAddressRegisterResponse response = memberService.registerMemberAddress(memberId, request);
		return ApiResponse.success(response);
	}

	@DeleteMapping("/{memberId}/address/{addressId}")
	public ApiResponse<MemberAddressRemoveResponse> removeMemberAddress(
		@PathVariable(name = "memberId") Long memberId,
		@PathVariable(name = "addressId") Long addressId
	) {
		MemberAddressRemoveResponse response = memberService.removeMemberAddress(memberId, addressId);
		return ApiResponse.success(response);
	}
}
