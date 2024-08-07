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
	public int updateByMember(Member member) {
		int result = 0;
		System.out.println("JpaMemberRepository updateByMember1 member -> " +member);
		Member member3 = em.find(Member.class, member.getId());
		
		//존재하면 수정
		if (member3 != null ) {
			System.out.println("JpaMemberRepository updateMember member.getTeamId() => " + member.getTeamid());
			Team team = em.find(Team.class, member.getTeamid());
				if(team != null) {
					team.setName(member.getTeamname());
					em.persist(team);
				}
				//회원 저장
				member3.setTeam(team);
				member3.setName(member.getName());
				em.persist(member3);
				result = 1;
				System.out.println("JpaMemberRepository updateByMember2 member-> " +member);
		}  else {
			result = 0;
			System.out.println("JapMemberRepository updateByMember No Exist...");
		}
		
		return result;
	}

	@Override
	public List<Member> findByName(String name) {
		System.out.println("JpaMemberRepository findByName name-->" + name);
		String pname = name + '%';
		List<Member> member1 = em.createQuery("select m from Member m where name like :name",Member.class )
							   .setParameter("name", pname)
							   .getResultList();
		System.out.println("JpaMemberRepository findByName after");
		return member1;
	}

	@Override
	public List<Member> findByMembers(Long id, Long sal) {
	    // 검색조건으로 입력한 id 큰거 , sal 큰거
		System.out.println("JpaMemberRepository findByMembers id--> "+id + " sal " + sal);
		List<Member> member1 = em.createQuery("Select m from Member m where sal>:sal AND id>:id",Member.class)
								 .setParameter("sal", sal)
								 .setParameter("id", id)
								 .getResultList();
		return member1;
	}

}
