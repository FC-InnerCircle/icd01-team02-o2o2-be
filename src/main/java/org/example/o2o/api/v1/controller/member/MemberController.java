package org.example.o2o.api.v1.controller.member;

import org.example.o2o.api.v1.docs.member.MemberDocsController;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRegisterRequest;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRegisterResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRemoveResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberRegisterRequest;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberRegisterResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberWithdrawalResponse;
import org.example.o2o.api.v1.service.member.MemberService;
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
@RequestMapping("/api/v1/members")
public class MemberController implements MemberDocsController {

	private final MemberService memberService;

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
