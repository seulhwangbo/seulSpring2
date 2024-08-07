package com.oracle.oBootJpa02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;
import com.oracle.oBootJpa02.service.MemberService;

//@SpringBootTest : 스프링 부트 띄우고 테스트(이게 없으면 @Autowired 다 실패)
//반복 가능한 테스트 지원, 각각의 테스트를 실행할 때마다 트랜잭션을 시작하고
//테스트가 끝나면 트랜잭션을 강제로 롤백

@SpringBootTest
@Transactional
public class MemberServiceTest {
	
	//field di
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;
	
	//테스트 하기 전에 실행할 Method 기본적으로 무조건 수행되어야 하는 과정이 있다면 @beforeEach를 수행하면 된다
	@BeforeEach
	public void before1() {
		System.out.println("Test @BeforeEach....");
	}
	
	@Test
	//@Rollback(value = false)
	// commit 해라
	public void memberSave() {
		// 1.조건
		Member member = new Member(); 
		member.setTeamname("카카오");
		member.setName("송혜선");
		// 2.행위 <action>
		Member member3 = memberService.memberSave(member);
		// 3.결과 <result>
		System.out.println("MemberServiceTest memberSave Test member3" + member3);
	}
	
	@Test
	public void memberFind() {
		// 조건
		// 회원조회 name = 미쑤기
		Long findId = 4L;
		// 2. 행위 < action >
		Member member = memberService.findByMember(findId);
		// 3. 결과
		System.out.println("MemberServiceTest memberFind member-> " +member);
	}
}
