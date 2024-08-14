package com.oracle.oBootMybatis01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.repository.MemberJpaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberJpaService {
	private final MemberJpaRepository memberJpaRepository;

	public void join(Member member) {
		System.out.println("MemberJpaService join start");
		memberJpaRepository.save(member);
		
	}

	public List<Member> getListAllMember() {
		System.out.println("MemberJpaService getListAllMember start...");
		List<Member> memberList = memberJpaRepository.findAll();
		return memberList;
		
	}

	public Optional<Member> findById(Long memberId) {
		System.out.println("MemberJpaService findById start...");
		Optional<Member> member = memberJpaRepository.findById(memberId);
		return member;
	}

	public void memberUpdate(Member member) {
		System.out.println("MemberJpaService memberUpdate start...");
		memberJpaRepository.updateByMember(member);
	}

	
}
