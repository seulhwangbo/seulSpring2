package com.oracle.oBootJpaApi01.controller;

import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// controller + responseBody
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
// logger logger = loggerFactory.getLogger
@Slf4j
@RequiredArgsConstructor
public class JpaRestApiController {
	private final MemberService memberService;

	//test용도
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("JpaRestApiController Start...");
		String hello = "안녕";
		// 	   stringConverter
		return hello;
	}
	//bad api
	@GetMapping("/restApi/v1/members")
	public List<Member> membersVer1(){
		System.out.println("JpaRestApiController / restApi/v1/members start .. ");
		List<Member> listMember = memberService.getListAllMember();
		return listMember;
	}
	
	//good api
	//이름 급여만 전송
	@GetMapping("/restApi/v21/members")
	public Result membersVer21() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController restApi/v21/members findMembers.size"+findMembers.size());
		List<MemberRtnDto> resultList = new ArrayList<MemberRtnDto>();
		//이전 목적 : 반드시 필요한 data만 보여준다 : 외부 노출 최대한 금지
		
		for(Member member : findMembers) {
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());
			System.out.println("restApi/v21/members getName -> " + memberRtnDto.getName());
			System.out.println("restApi/v21/members getSal -> " + memberRtnDto.getSal());
			resultList.add(memberRtnDto);
		}
		System.out.println("restApi/v21/members resultList.size()->" + resultList.size());
		return new Result(resultList.size(),resultList);
	}
	
	//Good API 람다 Version
	//이름과 급여만 전송하는 것이 목적이다
	//가장 좋은 방법
	@GetMapping("/restApi/v22/members")
	public Result membersVer22() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController restApi/v21/members findMembers.size"+findMembers.size());
		// 자바 8 에서 추가한 스트림은 람다를 활용할 수 있는 기술 중 하나이다
		List<MemberRtnDto> memberCollect = findMembers.stream()
													  .map(m->new MemberRtnDto(m.getName(), m.getSal()))
													  .collect(Collectors.toList())
													  ;
		
		System.out.println("restApi/v22/members memberCollect.size()=> "+memberCollect.size());
		return new Result(memberCollect.size(),memberCollect);
	}
	
	@AllArgsConstructor
	@Data
	class Result<T>{
		private final int totCount; // 총 인원수 추가
		private final T data;
	}
	
	@Data
	@AllArgsConstructor
	class MemberRtnDto {
		private String name;
		private Long   sal;
	}
}
