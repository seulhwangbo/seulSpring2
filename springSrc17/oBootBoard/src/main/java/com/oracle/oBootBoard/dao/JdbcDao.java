package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootBoard.dto.BDto;
@Repository
public class JdbcDao implements BDao {
	//JDBC 사용
	private final DataSource dataSource;
	
	@Autowired
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}

	@Override
	public ArrayList<BDto> boardList() {
		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "Select * from mvc_board";
		System.out.println("BDao boardList start....");
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet  = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int bId 	 	= resultSet.getInt("bId");
				String bName 	= resultSet.getString("bName");
				String bTitle	= resultSet.getString("bTitle");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				String bContent = resultSet.getString("bContent");
		        int bHit 		= resultSet.getInt("bHit");
		        int bGroup 		= resultSet.getInt("bGroup");
		        int bStep 		= resultSet.getInt("bStep");
		        int bIndent		= resultSet.getInt("bIndent");
		        BDto bdto 		= new BDto(bId,bName,bTitle,bContent,
		        					 bDate,bHit,bGroup,bStep,bIndent);
				bList.add(bdto);
				
			}
			return bList;
		} catch (Exception e) {
			throw new IllegalStateException(e);		} 
		finally {
			close(connection, preparedStatement, resultSet);
		}
	}
	
	@Override
	public BDto contentView(String strId) {
		BDto bDto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	
		String sql = "Select * FROM mvc_board Where bId=?";
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,Integer.parseInt(strId));
			resultSet = preparedStatement.executeQuery();
			upHit(strId);
			if(resultSet.next()) {
				int bId 	 	= resultSet.getInt("bId");
				String bName 	= resultSet.getString("bName");
				String bTitle	= resultSet.getString("bTitle");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				String bContent = resultSet.getString("bContent");
		        int bHit 		= resultSet.getInt("bHit");
		        int bGroup 		= resultSet.getInt("bGroup");
		        int bStep 		= resultSet.getInt("bStep");
		        int bIndent		= resultSet.getInt("bIndent");
		        bDto 			= new BDto(bId,bName,bTitle,bContent,
		        					 bDate,bHit,bGroup,bStep,bIndent);
				}
			return bDto;
			
		} catch (Exception e) {
			throw new IllegalStateException(e);		
		} finally {
			close(connection, preparedStatement, resultSet);
		}
	}

	private void upHit(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			String 	   sql  = "Update mvc_board set bHit=bHit+1 where bId =?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bId);
			int rn = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();		
		} finally {
				try {
					if (connection != null)  	  connection.close();
					if (preparedStatement !=null) preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

	private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		{
		try {
			if(resultSet != null) resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
		try {
			if(preparedStatement != null) preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();		
		}
		
		try {
			if(connection != null) close(connection);
		} catch (Exception e) {
			e.printStackTrace();		
		}
	
	}
		
	}

	private void close(Connection connection) throws SQLException {
		DataSourceUtils.releaseConnection(connection, dataSource);
		
	}


}
