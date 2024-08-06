package com.oracle.oBootJpa02.repository;

import java.util.List;

import com.oracle.oBootJpa02.domain.Member;

public interface MemberRepository {
		Member memberSave(Member member);
		List<Member> finaAll();
		Member findByMember(Long memberId);
		void updateByMember(Member member);
}



