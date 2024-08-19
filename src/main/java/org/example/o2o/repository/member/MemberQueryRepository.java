package org.example.o2o.repository.member;

import static org.example.o2o.domain.member.QMember.*;

import org.example.o2o.domain.member.Member;
import org.example.o2o.domain.member.QMember;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberQueryRepository {

	private final JPAQueryFactory factory;

	public Member findMemberById(Long id) {
		return factory.select(member)
			.from(member)
			.where(
				member.id.eq(id)
			)
			.fetchOne();
	}
}
