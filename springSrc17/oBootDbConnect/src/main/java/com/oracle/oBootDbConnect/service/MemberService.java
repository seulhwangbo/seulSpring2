package com.oracle.oBootDbConnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootDbConnect.domain.Member7;
import com.oracle.oBootDbConnect.repository.MemberRepository;
@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	//회원가입
	public Long memberSave(Member7 member7) {
		System.out.println("MemberService memberSave start...");
		memberRepository.save(member7);
		return member7.getId();
	}
	
	//전체회원 조회
	public List<Member7> findMember(){
		System.out.println("MemberService findMember start");
		List<Member7> memList = null;
		memList = memberRepository.findAll();
		return memList;
		//return memberRepository.findAll();
	}

}
