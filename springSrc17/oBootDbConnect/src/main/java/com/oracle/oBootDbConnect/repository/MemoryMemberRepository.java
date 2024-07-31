package com.oracle.oBootDbConnect.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootDbConnect.domain.Member7;
//@Repository
public class MemoryMemberRepository implements MemberRepository {

	private static Map<Long, Member7> store = new HashMap<Long, Member7>();
	private static Long sequence = 0L;
	
	@Override
	public Member7 save(Member7 member7) {
		member7.setId(++sequence);
		store.put(member7.getId(), member7);
		System.out.println("MemoryMemberRepsoitory sequence => " +sequence);
		System.out.println("MemoryMemberRepsoitory member7.getName()=>" +member7.getName());
		return member7;
	}

	@Override
	public List<Member7> findAll() {
		System.out.println("MemoryMemberRepsoitory findall start... ");
		List<Member7> listMember = new ArrayList<>(store.values());
		System.out.println("MemoryMemberRepsoitory findall slistMember.size()=>" + listMember.size());
		return listMember;
	}

}
