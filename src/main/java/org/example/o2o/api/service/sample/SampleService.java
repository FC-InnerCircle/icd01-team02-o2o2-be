package org.example.o2o.api.service.sample;

import org.example.o2o.domain.member.Member;
import org.example.o2o.repository.member.MemberQueryRepository;
import org.example.o2o.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SampleService {

	private final MemberRepository memberRepository;
	private final MemberQueryRepository queryRepository;

	public Member saveMember(Member member) {
		return memberRepository.save(member);
	}

	public Member findMemberById(Long id) {
		return memberRepository.findById(id).orElse(null);
	}

	public Member findMemberById2(Long id) {
		return queryRepository.findMemberById(id);
	}

}
