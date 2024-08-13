package com.oracle.oBootMybatis01.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dept {
	private int deptno;
	private String dname;
	private String loc;
}
