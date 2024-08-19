package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

import lombok.RequiredArgsConstructor;

@Repository
//final 끌고 들어와준다.
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
   //Mybatis DB 연동 (DI 작업)
   private final SqlSession session;

   @Override
   public int totalEmp() {
      int totEmpCount = 0;
      System.out.println("EmpDaoImpl Start totalEmp...");
      
      try {
         //.으로 이름 넣어주는 이유 : id가 많기 때문에!!
         //현재 logic을 통해 나온 totEmpCount는 COUNT(*)이다!
         totEmpCount = session.selectOne("com.oracle.oBootMyBatis01.EmpMapper.empTotal");
         System.out.println("EmpDaoImpl totalEmp totEmpCount-> "+totEmpCount);
      } catch (Exception e) {
         System.out.println("EmpDaoImpl totalEmp e.getMessage()->"+e.getMessage());
      }

      return totEmpCount;
   }

   @Override
   public List<Emp> listEmp(Emp emp) {
      List<Emp> empList = null;
      System.out.println("EmpDaoImpl listEmp Start...");
      try {
         //ID가 unique여야한다. (PK처럼 생각해야한다.)
         //                        Map ID      parameter
         empList = session.selectList("tkEmpListAll", emp);
         System.out.println("EmpDaoImpl listEmp empList.size()-> "+empList.size());
      } catch (Exception e) {
         System.out.println("EmpDaoImpl listEmp e.getMessage()-> "+e.getMessage());
      }
      return empList;
   }

   @Override
   public Emp detailEmp(int empno) {
//      2. EmpDao   detailEmp method 선언 
////    mapper ID   ,    Parameter
      //emp = session.selectOne("tkEmpSelOne",    empno);
      //System.out.println("emp-> "+emp1);
      
      Emp emp = new Emp();
      System.out.println("EmpDaoImpl detailEmp Start...");
      try {
         emp = session.selectOne("tkEmpSelOne", empno);
         System.out.println("emp-> "+emp.getEname());
      } catch (Exception e) {
         System.out.println("EmpDaoImpl detailEmp e.getMessage()-> "+e.getMessage());
      }
      return emp;
   }

@Override
public int updateEmp(Emp emp) {
	System.out.println("EmpDaoImpl updateEmp Start...");
	int updateCount = 0;
	try {
		updateCount = session.update("tkEmpUpdate",emp);
		System.out.println("emp => " +emp.getEname());
	} catch (Exception e) {
		System.out.println("EmpDaoImp updateEmp e.getMessage()-> "+e.getMessage());
	}
	return updateCount;
}

@Override
public List<Emp> listManager() {
	List<Emp> listManager = null;
	try {
		listManager = session.selectList("tkSelectManager");
	} catch (Exception e) {
		System.out.println("EmpDaoImpl listManager e.getMessage()->" + e.getMessage());
	}
	return listManager;
}

@Override
public int insertEmp(Emp emp) {
	int insertResult = 0;
	System.out.println("EmpDaoImpl insertEmp start");
	try {
		insertResult = session.insert("insertEmp",emp);
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("EmpDaoImpl insertEmp e.getMessage()->" + e.getMessage());
	}
	return insertResult;
}

@Override
public int deleteEmp(int empno) {
	System.out.println("EmpDaoImpl delete Start...");
	int result = 0;
	try {
		result = session.delete("deleteEmp", empno);
		System.out.println("EmpDaoImpl delete result" + result);
	} catch (Exception e) {
		System.out.println("EmpDaoImpl delete Exception" + e.getMessage());
	}
	return result;
}

@Override
public int condTotalEmp(Emp emp) {
	System.out.println("EmpDaoImpl condTotalEmp Start...");
	int totEmpCnt = 0;
	try {
		totEmpCnt = session.selectOne("condTotalEmp", emp);
		System.out.println("EmpDaoImpl condTotalEmp result" + totEmpCnt);
	} catch (Exception e) {
		System.out.println("EmpDaoImpl condTotalEmp Exception" + e.getMessage());
	}
	return totEmpCnt;
}

@Override
public List<Emp> empSearchList3(Emp emp) {
	System.out.println("EmpDaoImpl empSearchList3 start");
	List<Emp> searchEmp = null;
	try {
		searchEmp = session.selectList("tkEmpSearchList3", emp);
		System.out.println("EmpDaoImpl empSearchList3 result" + searchEmp);
	} catch (Exception e) {
		System.out.println("EmpDaoImpl empSearchList3 Exception" + e.getMessage());
	}
	return searchEmp;
}

@Override
public List<EmpDept> listEmpDept() {
	System.out.println("EmpDaoImpl listEmpDept start...");
	List<EmpDept> empDept = null;
	try {
		empDept = session.selectList("tkListEmpDept");
		System.out.println("EmpDaoImpl listEmpDept result" + empDept);
	} catch (Exception e) {
		System.out.println("EmpDaoImpl listEmpDept Exception" +e.getMessage());
	}
	return empDept;
}

@Override
public String deptName(int deptno) {
	System.out.println("EmpDaoImpl deptName start...");
	String str = "";
	try {
		System.out.println("EmpDaoImpl deptName deptno => " + deptno);
		str = session.selectOne("tkDeptName",deptno);
		System.out.println("EmpDaoImpl deptName str => " + str);
	} catch (Exception e) {
		System.out.println("EmpDaoImpl deptName Exception" + e.getMessage());
	}
	return str;
}

}
