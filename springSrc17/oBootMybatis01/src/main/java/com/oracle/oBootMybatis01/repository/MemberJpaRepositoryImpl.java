package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberJpaRepositoryImpl implements MemberJpaRepository {
	
	private final EntityManager em;
	
	@Override
	public Member save(Member member) {
		em.persist(member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("SELECT m FROM Member m", Member.class)
									.getResultList();		
		return memberList;
	}

	@Override
	public Optional<Member> findById(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return Optional.ofNullable(member);
		// null값도 던져주기 위해서 이렇게 만들고 판단은 controller가 한다
	}

	@Override
	public void updateByMember(Member member) {
		// 1.update
		// setting 된 것만 수정, 나머지는 null 값이다
		// em.merge(member);
		
		// 2.update
		Member member1 = em.find(Member.class, member.getId());
		member1.setName(member.getName());
		member1.setId(member.getId());
		
	}

}
