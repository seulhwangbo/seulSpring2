package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
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
	
}
