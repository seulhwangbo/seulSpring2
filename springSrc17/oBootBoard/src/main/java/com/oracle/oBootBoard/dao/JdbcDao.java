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

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
//@Repository
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
		String sql = "Select * from mvc_board order by bGroup desc, bStep asc";
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

	@Override
	public void modify(int bId, String bName, String bTitle, String bContent) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			//ResultSet resultSet = null;	
			try {
				connection = getConnection();
				String sql = "Update mvc_board SET bName=?, bContent=?, bTitle =? Where bId=?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, bName);
				preparedStatement.setString(2, bContent);
				preparedStatement.setString(3, bTitle);
				preparedStatement.setInt(4, bId);
				
				preparedStatement.executeUpdate();
				
				
			} catch (Exception e) {
					e.printStackTrace();		
				} finally {
						try {
							//if (resultSet != null)  	  resultSet.close();
							if (connection != null)  	  connection.close();
							if (preparedStatement !=null) preparedStatement.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}

	@Override
	public void modify3(BDto bDto) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	

		try {
			connection = getConnection();
			String sql = "Update mvc_board SET bName=?, bContent=?, bTitle =? Where bId=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bDto.getbName());
			preparedStatement.setString(2, bDto.getbContent());
			preparedStatement.setString(3, bDto.getbTitle());
			preparedStatement.setInt(4, bDto.getbId());
			int bHit 		= resultSet.getInt("bHit");
	        int bGroup 		= resultSet.getInt("bGroup");
	        int bStep 		= resultSet.getInt("bStep");
	        int bIndent		= resultSet.getInt("bIndent");
	//        bDto 			= new BDto(bId,bName,bTitle,bContent,      					 bDate,bHit,bGroup,bStep,bIndent);

			
			preparedStatement.executeUpdate();
			
			
		} catch (Exception e) {
				e.printStackTrace();		
			} finally {
					try {
						//if (resultSet != null)  	  resultSet.close();
						if (connection != null)  	  connection.close();
						if (preparedStatement !=null) preparedStatement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		
	}

	@Override
	public void delete(int bid) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			String sql = "Delete From mvc_board where bId=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bid);
			preparedStatement.executeUpdate();
			
	}catch (Exception e) {
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

	@Override
	public void write(String bName, String bTitle, String bContent) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			String sql = "INSERT INTO mvc_board (bId, bName,bTitle,bContent, bHit, bGroup, bStep, bIndent, bDate) " +
                    "VALUES (mvc_board_seq.NEXTVAL,?, ?, ?, 0, mvc_board_seq.currval, 0, 0, SYSDATE)";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, bName); 
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent); 
			
			preparedStatement.executeUpdate();

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

	@Override
	public BDto reply_view(int bId) {
	    BDto dto = null;
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = getConnection();
	        String sql = "SELECT * FROM mvc_board WHERE bId=?";
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, bId);
	        resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            int id 		 	= resultSet.getInt("bId");   
	            String bName 	= resultSet.getString("bName");   
	            String bTitle 	= resultSet.getString("bTitle");   
	            String bContent = resultSet.getString("bContent");   
	            Timestamp bDate = resultSet.getTimestamp("bDate");   
	            int bHit 		= resultSet.getInt("bHit");   
	            int bGroup 		= resultSet.getInt("bGroup");   
	            int bStep 		= resultSet.getInt("bStep");   
	            int bIndent 	= resultSet.getInt("bIndent");   
	            dto = new BDto(id, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
	        }
	    } catch (Exception e) {
	        // 적절한 예외 처리 필요 (예: 로그 기록)
	        e.printStackTrace();
	    } finally {
	        try {
	            if (resultSet != null) resultSet.close();
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            // 적절한 예외 처리 필요 (예: 로그 기록)
	            e.printStackTrace();
	        }
	    }
	    return dto;
	}

	@Override
	public BDto reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent) {
	BDto bdto = null;
    replyShape(bGroup, bStep);
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    String sql = "INSERT INTO mvc_board (bName,bTitle,bContent, bDate) " +
            "VALUES (?, ?, ?, SYSDATE)";
    
    try {
        connection = getConnection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, bName);
        preparedStatement.setString(2, bTitle);
        preparedStatement.setString(3, bContent);

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return bdto;
}

	private void replyShape(int bGroup, int bStep) {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    String sql = "UPDATE mvc_board SET bStep = bStep + 1, bIndent = bIndent + 1 WHERE bGroup = ? AND bStep > ?";
	    
	    try {
	        connection = getConnection();
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, bGroup);
	        preparedStatement.setInt(2, bStep); // bStep 기준으로만 업데이트
	        
	        int rowsUpdated = preparedStatement.executeUpdate();
	        // 업데이트된 행 수를 확인할 수 있음, 필요에 따라 활용 가능
	        System.out.println("Rows updated: " + rowsUpdated);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (preparedStatement != null) preparedStatement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
}

	
	






//	@Override
//	public void write(BDto bDto) {
//		try {
//			connection = getConnection();
//			String sql = "INSERT INTO mvc_board (bId, bGroup, bHit, bStep, bIndent, bDate, bName, bContent, bTitle) " +
//                    "VALUES (mvc_board_seq.NEXTVAL,mvc_board_seq.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?, ?)";
//
//			preparedStatement = connection.prepareStatement(sql);
//       
//			preparedStatement.setInt(1, bDto.getbHit()); 
//			preparedStatement.setInt(2, bDto.getbStep());   
//			preparedStatement.setInt(3, bDto.getbIndent()); 
//			preparedStatement.setString(4, bDto.getbName()); 
//			preparedStatement.setString(5, bDto.getbContent());
//			preparedStatement.setString(6, bDto.getbTitle()); 
//			
//			preparedStatement.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();		
//		} finally {
//				try {
//					if (connection != null)  	  connection.close();
//					if (preparedStatement !=null) preparedStatement.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		try {
//			connection = getConnection();
//			String sql = "INSERT INTO mvc_board (bId, bGroup, bHit, bStep, bIndent, bDate, bName, bContent, bTitle) " +
//                    "VALUES (mvc_board_seq.NEXTVAL,mvc_board_seq.NEXTVAL, ?, ?, ?, SYSDATE, ?, ?, ?)";
//
//			preparedStatement = connection.prepareStatement(sql);
//       
//			preparedStatement.setInt(1, bDto.getbHit()); 
//			preparedStatement.setInt(2, bDto.getbStep());   
//			preparedStatement.setInt(3, bDto.getbIndent()); 
//			preparedStatement.setString(4, bDto.getbName()); 
//			preparedStatement.setString(5, bDto.getbContent());
//			preparedStatement.setString(6, bDto.getbTitle()); 
//			
//			preparedStatement.executeUpdate();
//
//		} catch (Exception e) {
//			e.printStackTrace();		
//		} finally {
//				try {
//					if (connection != null)  	  connection.close();
//					if (preparedStatement !=null) preparedStatement.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}


