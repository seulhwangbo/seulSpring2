package com.oracle.oBootJpa02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MemberService {
	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member memberSave(Member member) {
		System.out.println("MemberService join member->" + member);
		memberRepository.memberSave(member);
		return member;
	}

	public List<Member> getListAllMember() {
		List <Member> listMember = memberRepository.finaAll();
		System.out.println("MemberService getListAllMember listMember.size" + listMember.size());
		return listMember;
	}

	public Member findByMember(Long memberId) {
		Member member1 = memberRepository.findByMember(memberId);
		System.out.println("MemberService findByMember member1->" + member1);
		return member1;
	}

	public void memberUpdate(Member member) {
		System.out.println("memberservice memberUpdate member->" + member);
		memberRepository.updateByMember(member);
		System.out.println("memberService memberUpdate memberRepository.updateByMember After...");
		return;
		
	}
}
