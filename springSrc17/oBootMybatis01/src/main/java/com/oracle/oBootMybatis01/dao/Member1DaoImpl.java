package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl implements Member1Dao {
   
	private final PlatformTransactionManager transactionManager;
    private final SqlSession session;
   
   
   @Override
   public int memCount(String id) {
      // Mapper --> Member1.xml
      // result = session.selectOne("memCount", id);
      System.out.println("Member1DaoImpl memCount start.....");
      int result = 0;
      try {
         result = session.selectOne("memCount", id);
         System.out.println("Member1DaoImpl memCount memCount(result)-> "+result);
      } catch (Exception e) {
         System.out.println("Member1DaoImpl memCount e.getMessage()-> "+e.getMessage());
      }
      return result;
   }

   @Override
   public List<Member1> listMem(Member1 member1) {
      List<Member1> listMem = null;
      System.out.println("Member1DaoImpl listMem start.....");
      
      try {
         listMem = session.selectList("listMember1",member1);
         System.out.println("Member1DaoImpl listMem member-> "+member1);
      } catch (Exception e) {
         System.out.println("Member1DaoImpl listMem e.getMessage()-> "+e.getMessage());
      }
      return listMem;
   }

	@Override
	public int transactionInsertUpdate() {
		int result = 0;
		System.out.println("Member1DaoImpl transactionInsertUpdate Start...");
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		
		try {
			member1.setId("1005");
			member1.setPassword("2345");
			member1.setName("써니1");
			
			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate member1 result" + result);
			
			member2.setId("1007");
			member2.setPassword("3456");
			member2.setName("쏜히1");
			
			result = session.insert("insertMember1",member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate member2 result => " + result);
		} catch (Exception e) {
			System.out.println("Member1DaoImpl transactionInsertUpdate Exception => " + e.getMessage());
			result = -1;
		}
		return result;
	}

	@Override
	public int transactionInsertUpdate3() {
		int result = 0;
		System.out.println("Member1DaoImpl transactionInsertUpdate3 Start...");
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();
		// Transaction관리는 transactionManager의 getTransaction을가지고 상태따라 설정
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			// 두개의 test 하나는 성공 하나는 실패
			// 실행할 때마다 자동 commit
			member1.setId("1012");
			member1.setPassword("2345");
//			member1.setName("써니1");
			
			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member1 result" + result);
			
			member2.setId("1013");
			member2.setPassword("3456");
			member2.setName("쏜히1");
			
			result = session.insert("insertMember1",member2);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 member2 result => " + result);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			System.out.println("Member1DaoImpl transactionInsertUpdate3 Exception => " + e.getMessage());
			result = -1;
		}
		return result;
	}
}
