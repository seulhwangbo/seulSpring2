package com.oracle.oBootMybatis01.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DeptDaoImpl implements DeptDao {
	
	//MyBatis 연동
	private final SqlSession session;
	@Override
	public List<Dept> deptSelect() {
		List <Dept> deptSelect = null;
		try {
			deptSelect = session.selectList("tkSelectDept");
		} catch (Exception e) {
			System.out.println("DeptDaoImpl deptSelect e.getMessage()->" + e.getMessage());
		}
		return deptSelect;
	}
	//프로시저를 뽑는 회사도 있기 때문에 하나 이상은 구현하는 것이 도움ㅇ ㅣ된다
	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("DeptDaoImpl insertDept Start...");
		session.selectOne("procDeptInsert",deptVO);
	}
	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("DeptDaoImpl selListDept Start...");
		//ResultMap은 DB 컬럼명과 DTO의 변수 명이 다를 때 사용
		session.selectOne("procDeptList", map);
	}

}
