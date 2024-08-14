package com.oracle.oBootMybatis01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberJpaController {
	private final MemberJpaService memberJpaService;

	@GetMapping(value = "/memberJpa/new")
	public String createForm() {
		System.out.println("MemberController/members/new Start...");
		return "memberJpa/createMemberForm";
	}
	
	@PostMapping(value = "/memberJpa/save")
	public String create(Member member) {
		System.out.println("MemberController memberJpa/save start..");
		System.out.println("member.getId()=> " +member.getId());
		memberJpaService.join(member);
		
		return "memberJpa/createMemberForm";
	}
	
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		System.out.println("MemberController listMember Start..");
		List<Member> memberList = memberJpaService.getListAllMember();
		model.addAttribute("members",memberList);
		return "memberJpa/memberList";
	}
	
	@GetMapping(value = "/memberJpa/memberUpdateForm")
	public String memberUpdateForm(Member member1, Model model) {
		Member member = null;
		String rtnJsp = "";
		System.out.println("MemberController memberUpdateForm id=> " +member1.getId());
		Optional<Member> maybeMember = memberJpaService.findById(member1.getId());
		if(maybeMember.isPresent()) {
			System.out.println("MemberController memberUpdate maybeMember is not NULL");
			member = maybeMember.get();
			model.addAttribute("member",member);
			model.addAttribute("message","member가 존재해요. 수정 수행해요");
			rtnJsp = "memberJpa/memberModify";
		} else {
			System.out.println("MemberController memberUpdate maybeMember is NULL");
			model.addAttribute("message","member가 존재하지 않아요. 입력부터 해요");
			rtnJsp = "forward:/members";
		}
		return rtnJsp;
	}
	
	@GetMapping(value = "/memberJpa/memberUpdate")
	public String memberUpdate(Member member,Model model) {
		System.out.println("MemberController memberUpdate member->" +member);
		memberJpaService.memberUpdate(member);
		return "redirect:/members";
	}
}