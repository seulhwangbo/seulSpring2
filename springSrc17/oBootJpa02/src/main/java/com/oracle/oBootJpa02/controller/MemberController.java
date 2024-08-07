package com.oracle.oBootJpa02.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private final MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping(value = "/members/new")
	public String createForm() {
		System.out.println("MemberController / members/ new start...");
		return "members/createMemberForm";
	}
	
	@PostMapping(value = "/memberSave")
	public String memberSave(Member member) {
		System.out.println("MemberController create start...");
		System.out.println("Member->" + member);
		System.out.println("member.getName() ->" + member.getName());
		memberService.memberSave(member);
		return "redirect:/";
	}
	
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		List<Member> memberList = memberService.getListAllMember();
		System.out.println("memberList.get(0).getName()=>" +memberList.get(0).getName());
		model.addAttribute("memberList", memberList);
		return "members/memberList";
	}
	
	@GetMapping(value ="/memberModifyForm")
	public String memberModify(Member member, Model model) {
		System.out.println("memberController memberModify id" + member.getId());
		Member member3 = memberService.findByMember(member.getId());
		System.out.println("member=>" + member3);
		model.addAttribute("member", member3);
		
		return "members/memberModify";
	}
	
	@PostMapping(value = "/members/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		System.out.println("MemberController memberUpdate member -> " + member);
		memberService.memberUpdate(member);
		System.out.println("MemberController memberUpdate memberService.updateByMember After...");
		return "redirect:/members";
	}
	
	@GetMapping(value = "/members/search")
	public String search(Member member, Model model) {
		List<Member> memberList = memberService.getListSearchMember(member.getName());
		System.out.println("MemberController/members/search memberList.size()->" + memberList.size());
		model.addAttribute("memberList",memberList);
		return "members/memberList";
	}
	
	@GetMapping(value = "findByListMembers")
	public String findByListMembers(Member member, Model model) {
		// List<Member> listMember  = memberRepository.findByMembers(member.getId(),member.getSal());
	    // 검색조건으로 입력한 id 큰거 , sal 큰거
		List<Member> memberList = memberService.getListFindByMembers(member);
		System.out.println("memberList.get(0).getName()->"+memberList.get(0).getName());
		System.out.println("memberList.get(0).getTeam().getName()->"+memberList.get(0).getTeam().getName());
		model.addAttribute("memberList",memberList);
		return "members/memberList";
	}
	
	
}
