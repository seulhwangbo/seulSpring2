package com.oracle.oBootHello.service;

import java.util.List;

import com.oracle.oBootHello.dto.Member1;
import com.oracle.oBootHello.repository.MemberRepository;
import com.oracle.oBootHello.repository.MemoryMemberRepository;

public class MemberService {
	// 전통적인 방식
	// interFace 							 실제 구현 클래스
	MemberRepository memberRepository = new MemoryMemberRepository();
	
	public Long memberSave(Member1 member1) {
		System.out.println("MemberService memberSabe start...");
		memberRepository.save(member1);
		return member1.getId();
	}
	
	public List<Member1> allMembers(){
		System.out.println("MemberService allMembers start...");
		List<Member1> memList = null;
		memList = memberRepository.findAll();
		System.out.println("memList.size()=>" + memList.size());
		return memList;
	}
	
}
