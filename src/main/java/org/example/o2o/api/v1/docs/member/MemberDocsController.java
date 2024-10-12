package org.example.o2o.api.v1.docs.member;

import org.example.o2o.api.v1.dto.member.MemberDto;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRegisterResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberAddressRemoveResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberRegisterResponse;
import org.example.o2o.api.v1.dto.member.MemberDto.MemberWithdrawalResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

public interface MemberDocsController {

	@Operation(summary = "회원 정보 등록", description = "회원 정보를 등록합니다.")
	org.example.o2o.common.dto.ApiResponse<MemberRegisterResponse> registerMember(
		@RequestBody @Valid MemberDto.MemberRegisterRequest request
	);

	@Operation(summary = "회원 탈퇴", description = "회원 정보를 탈퇴합니다.")
	org.example.o2o.common.dto.ApiResponse<MemberWithdrawalResponse> withdrawalMember(
		@PathVariable(name = "memberId") Long memberId
	);

	@Operation(summary = "회원 주소 등록", description = "회원의 주소 정보를 등록합니다.")
	org.example.o2o.common.dto.ApiResponse<MemberAddressRegisterResponse> registerMemberAddress(
		@PathVariable(name = "memberId") Long memberId,
		@RequestBody @Valid MemberDto.MemberAddressRegisterRequest request
	);

	@Operation(summary = "회원 주소 삭제", description = "회원의 주소 정보를 삭제합니다.")
	org.example.o2o.common.dto.ApiResponse<MemberAddressRemoveResponse> removeMemberAddress(
		@PathVariable(name = "memberId") Long memberId,
		@PathVariable(name = "addressId") Long addressId
	);
}
