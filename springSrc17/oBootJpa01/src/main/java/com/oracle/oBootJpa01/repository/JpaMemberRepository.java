package com.oracle.oBootJpa01.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa01.domain.Member;

import jakarta.persistence.EntityManager;
@Repository
public class JpaMemberRepository implements MemberRepository {
	// JPA DML ==> EntityManager 필수 ****
	private final EntityManager em;
	
	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public Member memberSave(Member member) {
		//.persist가 저장하는 method이다.
		em.persist(member);
		System.out.println("JpaMemberRepository memberSave member After...");
		return member;
	}

	@Override
	public List<Member> findAllMember() { //객체 table x 이것이 jpql
		// table 멤버가 아닌 객체 멤버라는 사실을 기억해야 헷갈리지 않게 알 수 있음을 기억하기
		List<Member> memberList = em.createQuery("select m from Member m", Member.class)
									.getResultList();
		System.out.println("JpaMemberRepository findAllMember memberList.size()->"+memberList.size());
		return memberList;
		
	}
	@Override
	public List<Member> findByNames(String searchName) {
		String pname = searchName + '%';
		System.out.println("JpaMemberRepository findByNames pname -> "+pname);
		List<Member> memberList = em.createQuery("select m from Member m where name like :name",Member.class)
									.setParameter("name", pname)
									.getResultList()
									;

		System.out.println("JpaMemberRepository memberList.size()->" + memberList.size());
		return memberList;
	}

}
