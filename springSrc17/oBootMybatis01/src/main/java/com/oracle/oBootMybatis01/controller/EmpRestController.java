package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
// @Controller + @ResponseBody
public class EmpRestController {
		
	private final EmpService es;
	
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("EmpRestController start....");
		String hello = "안녕";
		//     stringConverter
		return hello;
	}
	
	//http://jsonviewer.stack.hu/ 사이트를 이용한다
	@RequestMapping("/sample/sendVO2")
	public SampleVO sendVO(Dept dept) {
		System.out.println("@RestController dept.getDeptno()->" + dept.getDeptno());
		SampleVO vo = new SampleVO();
		vo.setFirstName("혜선");
		vo.setLastName("송");
		vo.setMno(dept.getDeptno());
	
		return vo;
	}
	
	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3(){
		System.out.println("@RestController sendVO3 Start...");
		List<Dept> deptList = es.deptSelect();
		return deptList;
	}
	
	// 결과 text 전송
	@RequestMapping("/empnoDelete")
	public String empnoDelete(Emp emp) {
		System.out.println("@RestController empnoDelete Start");
		System.out.println("@RestController empnoDelete emp" + emp);
		int    delStatus 	= es.deletEmp(emp.getEmpno());
		String delStatusStr = Integer.toString(delStatus);
		return delStatusStr;
	}
	// 결과 객체 전송
	@RequestMapping("/empnoDelete03")
	public Map<String, Object> empnoDelete03(Emp emp) {
		System.out.println("@RestController empnoDelete03 Start");
		System.out.println("@RestController empnoDelete03 emp" + emp);
		int    				delStatus = es.deletEmp(emp.getEmpno());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("delStatus", delStatus);
		return resultMap;
	}
	
	
}
