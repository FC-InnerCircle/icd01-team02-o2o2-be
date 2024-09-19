package org.example.o2o.repository.member;

import java.util.Optional;

import org.example.o2o.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query("""
			SELECT m
			FROM Member m
			WHERE m.id = :memberId
			AND m.status = org.example.o2o.domain.member.MemberStatus.ACTIVE
		""")
	Optional<Member> findActiveMemberById(@Param("memberId") Long memberId);

	@Query("""
			SELECT m
			FROM Member m
			LEFT JOIN FETCH m.addresses ma
			WHERE m.id = :memberId
			AND ma.status = org.example.o2o.domain.member.AddressStatus.ACTIVE
		""")
	Optional<Member> findMemberWithActiveAddressById(@Param("memberId") Long memberId);
}
