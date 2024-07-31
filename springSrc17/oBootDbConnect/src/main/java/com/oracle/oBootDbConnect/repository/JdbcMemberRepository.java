package com.oracle.oBootDbConnect.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootDbConnect.domain.Member7;

@Repository
public class JdbcMemberRepository implements MemberRepository {
	//JDBC 사용
	private final DataSource dataSource;
	@Autowired
	public JdbcMemberRepository(DataSource dataSource) {
			this.dataSource = dataSource;
	}
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
	
	@Override
	public Member7 save(Member7 member7) {
		String sql = "Insert into member7(id,name) Values(Member_seq.nextval,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member7.getName());
			pstmt.executeUpdate();
			return member7;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}
		
	

	@Override
	public List<Member7> findAll() {
		List<Member7> memlist = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "Select * From member7";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
		    while (rs.next()) {
	            Member7 member = new Member7();
	            member.setId(rs.getLong("id"));
	            member.setName(rs.getString("name"));
	            memlist.add(member);
	        }
			return memlist;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}
		
		
	
	
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) 
		{
		try {
			if(rs != null) rs.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
		try {
			if(pstmt != null) pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
		try {
			if(conn != null) close(conn);
		} catch (Exception e) {
			e.printStackTrace();		
		}
	
	}
	private void close(Connection conn) throws SQLException{
		DataSourceUtils.releaseConnection(conn, dataSource);
	}

}
