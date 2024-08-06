package com.oracle.oBootJpa02.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.domain.Team;

import jakarta.persistence.EntityManager;
@Repository
public class JpaMemberRepository implements MemberRepository {
	
	private final EntityManager em;
	
	@Autowired
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Member memberSave(Member member) {
	// 1. 팀 저장 
		Team team = new Team(); //인스턴스 선언
		team.setName(member.getTeamname());
		em.persist(team); // team 저장

		// 2. 회원 저장
		// FK만 맞춰주면 된다
		member.setTeam(team); //내가 잡아준 team id 의 sequence 번호를 맞춰주기 때문에 team = 회원 
		em.persist(member);
		return member;
	}

	@Override
	public List<Member> finaAll() {
		List<Member> memberList = em.createQuery("select m from Member m", Member.class).
								  getResultList();
		return memberList;
	}

	@Override
	public Member findByMember(Long memberId) {
		Member member = em.find(Member.class, memberId);
		return member;
	}

	@Override
	public void updateByMember(Member member) {
		// TODO Auto-generated method stub
		
	}

}
