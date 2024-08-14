package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl implements Member1Dao {
   
   public final SqlSession session;
   
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

}
