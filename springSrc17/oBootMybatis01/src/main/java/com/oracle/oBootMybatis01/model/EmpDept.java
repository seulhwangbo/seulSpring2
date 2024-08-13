package com.oracle.oBootMybatis01.model;

import lombok.Data;

//Join 목적
@Data
public class EmpDept {
	//Emp용
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;
	private int sal;
	private int comm;
	private int deptno;
	
	//데이터가 많지 않으면 원래 사용한 곳에 uppand 해도 괜찮지만 많다는 가정으로 선언
	//Dept용<많다는 가정>
	private String dname;
	private String loc;
}
