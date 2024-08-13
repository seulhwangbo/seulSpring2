package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpaApi01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {
	//JPA는 반드시 Entity Manager를 써야한다. -> 이 이후에 injection이 되어야한다.
	//										생성자 or @RequiredArgsConstructor로!!
	private final EntityManager em;
	
	//final은 반드시 생성자에 들어가야한다. instance를 받아줄 곳이 없다.
	//따라서 final은 생성자를 반드시 넣어주어야 한다.
	// 생성자에 넣어주지 않으면, EntityManager를 구현할 수 가 없다.
	// 하지만 이 대신에 @RequiredArgsConstructor 가 이것과 같은 역할을 한다.
//	@Autowired
//	public JpaMemberRepository(EntityManager em) {
//		this.em = em;
//	}

	@Override
	public Long save(Member member) {
		System.out.println("JpaMemberRepository save before...");
		em.persist(member);
		return member.getId();
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList = em.createQuery("SELECT m FROM Member m", Member.class)
									.getResultList();
		System.out.println("JpaMemberRepository findAll memberList.size()-> "+memberList.size());
		return memberList;
	}

	@Override
	public Member findByMember(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return member;
	}

	@Override
	public int updateByMember(Member member) {
		int result = 0;
		Member member3 = em.find(Member.class, member.getId());
		if (member3 != null) {
			// 회원 저장
			member3.setName(member.getName());
			member3.setSal(member.getSal());
			result = 1;
			System.out.println("JpaMemberRepository updateByMember Update...");
		} else {
			result = 0;
			System.out.println("JpaMemberRepository updateByMember No Exist..");
		}
		return result;
	}

}