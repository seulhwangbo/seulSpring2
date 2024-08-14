package com.oracle.oBootMybatis01.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.dao.Member1Dao;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	private final EmpDao ed;
	private final DeptDao dd;
	private final Member1Dao md;
	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl totalEmp start...");
		int totEmpCnt = ed.totalEmp();
		System.out.println("EmpServiceImpl totalEmp totEmpCnt->"+totEmpCnt);
		return totEmpCnt;
	}
	
	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpi listManager Start...");
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size()->" +empList.size());
		return empList;
		
	}

	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpServiceImpl detailEmp...");
		Emp emp = null;
		emp = ed.detailEmp(empno);
		return emp;
	}

	@Override
	public int updateEmp(Emp emp) {
		
		System.out.println("EmpService updateEmp start..");
		int updateCount = 0;
		updateCount = ed.updateEmp(emp);
		System.out.println("EmpService updateEmp..");
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		System.out.println("EmpService listManager start...");
		List<Emp> listManager = null;
		listManager = ed.listManager();
		return listManager;
	}

	@Override
	public List<Dept> deptSelect() {
		System.out.println("EmpService deptSelect start...");
		List<Dept> deptSelect = null;
		deptSelect = dd.deptSelect();
		return deptSelect;
	}

	@Override
	public int insertEmp(Emp emp) {
		System.out.println("EmpService insertEmp start..");
		System.out.println("EmpService insertEmp..");
		int insertResult = ed.insertEmp(emp);
		return insertResult;
	}

	@Override
	public int deletEmp(int empno) {
		int result = 0;
		System.out.println("EmpService deleteEmp start...");
		result = ed.deleteEmp(empno);
		System.out.println("EmpService deleteEmp...");
		return result;
	}

	@Override
	public int condTotalEmp(Emp emp) {
		System.out.println("EmpService condTotalEmp start...");
		int totalEmpCnt = ed.condTotalEmp(emp);
		System.out.println("EmpService condTotalEmp..." + totalEmpCnt);
		return totalEmpCnt;
	}

	@Override
	public List<Emp> listSearchEmp(Emp emp) {
		List<Emp> searchEmp = null;
		System.out.println("EmpService listSearchEmp start...");
		searchEmp = ed.empSearchList3(emp);
		System.out.println("EmpService listSearchEmp .. "+searchEmp);
		return searchEmp;
	}

	@Override
	public List<EmpDept> listEmpDept() {
		List<EmpDept> empDeptlist = null;
		System.out.println("EmpService listEmpDept start...");
		empDeptlist = ed.listEmpDept();
		System.out.println("EmpService listEmpDept .. " +empDeptlist.size());
		return empDeptlist;
	}

	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("EmpService insertDept Start...");
		dd.insertDept(deptVO);
	}

	@Override
	public void selListDept(HashMap<String, Object> map) {
		System.out.println("EmpService selListDept Start...");
		dd.selListDept(map);
	}

	  @Override
	   public int memCount(String id) {
	      System.out.println("EmpServiceImpl memCount Start...");
	      System.out.println("EmpServiceImpl memCount id -->"+id);
	      
	      return md.memCount(id);
	   }

	   @Override
	   public List<Member1> listMem(Member1 member1) {
	      List<Member1> listMem = null;
	      System.out.println("EmpServiceImpl listMem Start...");
	      listMem = md.listMem(member1);
	      System.out.println("EmpServiceImpl listMem.size() -->"+listMem.size());
	      return listMem;
	   }
}