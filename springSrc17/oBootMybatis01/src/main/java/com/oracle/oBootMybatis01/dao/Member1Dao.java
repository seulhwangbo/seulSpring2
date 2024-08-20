package com.oracle.oBootMybatis01.dao;

import java.util.List;

import com.oracle.oBootMybatis01.model.Member1;

public interface Member1Dao {
	// member1의 Count;
	int			  memCount(String id); 
	List<Member1> listMem(Member1 member1);
	int           transactionInsertUpdate();  // f
	int           transactionInsertUpdate3(); // t
}
