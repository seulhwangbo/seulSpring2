package com.oracle.oBootHello.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.oBootHello.dto.Member1;

public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member1> store = new HashMap<Long, Member1>();
	private static Long sequence = 0L;
	
	@Override
	public Member1 save(Member1 member1) {
		member1.setId(++sequence);
		store.put(member1.getId(), member1);
		System.out.println("MemoryMemberRepsoitory sequence => " +sequence);
		System.out.println("MemoryMemberRepsoitory member1.getName()=>" +member1.getName());
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepsoitory findall start... ");
		List<Member1> listMember = new ArrayList<>(store.values());
		System.out.println("MemoryMemberRepsoitory findall slistMember.size()=>" + listMember.size());
		return listMember;
	}

}
