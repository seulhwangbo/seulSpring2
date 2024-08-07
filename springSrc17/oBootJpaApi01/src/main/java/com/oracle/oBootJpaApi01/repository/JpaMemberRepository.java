package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootJpaApi01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 자동 injection
@Repository
public class JpaMemberRepository implements MemberRepository {

	
	private final EntityManager em;
	
	@Override
	public Long save(Member member) {
		em.persist(member);
		return member.getId();
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList= em.createQuery("select m from Member m",Member.class)
								   .getResultList();
		System.out.println("JpaMemberRepository findAll memberList.size()=>" + memberList.size());
		return memberList;
	}

}
