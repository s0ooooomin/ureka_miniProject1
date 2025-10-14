package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.DBManager;
import jdbc.dto.OrderDTO;

public class OrderDAO {

	// 접근 주소
	static String url = "jdbc:mysql://localhost:3306/dvdvdeep";
	static String user = "root";
	static String pwd = "0000";
	
	public static void main(String[] args) {

//		OrderDAO orderDao = new OrderDAO();
//		int ret = -1;
//		
//		try {
//			int price = orderDao.ReturnProcess(1);
//			orderDao.commit();
//			System.out.println(price + "원입니다.");
//
//		} catch (SQLException e) {
//			System.err.println("오류 발생! 작업을 롤백합니다.");
//	        e.printStackTrace();
//	        orderDao.rollback();
//		}
//		
//		
//		System.out.println(ret);
//		
	}
	
	private Connection con;
	
	// Orders
	// return Order 
	// update returndate && 비용계산 (return-borrow * price)
	// -> sales에 수입 insert, orders에서 delete
	public int ReturnProcess(int order_id) throws SQLException {
        // 1. DB에 연결하고 트랜잭션을 시작합니다. (AutoCommit 비활성화)
        con = DriverManager.getConnection(url, user, pwd);
        con.setAutoCommit(false);

        // 2. returndate를 오늘 날짜로 업데이트합니다. (아직 임시 상태)
        String updateSql = "UPDATE orders SET returndate = CURDATE() WHERE order_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(updateSql)) {
            pstmt.setInt(1, order_id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("정상적으로 이루어지지 않았습니다.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e; // 여기서 문제 발생
        }

        // 3. 방금 업데이트된 정보를 기준으로 최종 요금을 계산하여 가져옵니다.
        String selectSql = "SELECT o.movie_id, (DATEDIFF(o.returndate, o.borrowdate) + 1) * m.price AS saleprice " +
                         "FROM orders o " +
                         "JOIN movies m ON o.movie_id = m.movie_id " +
                         "WHERE o.order_id = ?";
        int saleprice = 0;
        int movie_id = 0;
        try (PreparedStatement pstmtSelect = con.prepareStatement(selectSql)) {
            pstmtSelect.setInt(1, order_id);
            try (ResultSet rs = pstmtSelect.executeQuery()) {
                if (rs.next()) {
                    saleprice = rs.getInt("saleprice");
                    movie_id = rs.getInt("o.movie_id");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e; // 여기서 문제 발생

        }
        
        // 4. 방금 얻은 saleprice를 order에 입력
        String updateSaleprice = "UPDATE orders SET saleprice = ? WHERE order_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(updateSaleprice)) {
            
        	pstmt.setInt(1, saleprice);
        	pstmt.setInt(2, order_id);
        	
            int ret = pstmt.executeUpdate();
            if (ret != 1) {
                throw new SQLException("정상적으로 이루어지지 않았습니다1.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e; // 여기서 문제 발생

        }
        
        // 5. insert sales
        String insertSales = "insert into sales values ( default, ?, ? , CURDATE() );";
        int ret = -1;
        try (PreparedStatement pstmt = con.prepareStatement(insertSales)) {
        	
        	pstmt.setInt(1, movie_id);
        	pstmt.setInt(2, saleprice);

        	ret = pstmt.executeUpdate();
        	if (ret != 1) {
                throw new SQLException("정상적으로 이루어지지 않았습니다2.");
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e; // 여기서 문제 발생

        }
        
        // 6. 반납된 주문은 삭제
        String deleteSql = "delete from orders where order_id = ?;";
        ret = -1;
        try (PreparedStatement pstmt = con.prepareStatement(deleteSql)){
			
			// 파라미터
			pstmt.setInt(1, order_id);
			
			ret = pstmt.executeUpdate();
			if (ret != 1) {
                throw new SQLException("정상적으로 이루어지지 않았습니다3.");
            }
		
		} catch (Exception e) {
			e.printStackTrace();
            throw e; // 여기서 문제 발생

		}
        
        // 아직 DB에 완전 적용된 것이 아님
        return saleprice;
        
    }

    /**
     * 트랜잭션을 DB에 최종 반영(COMMIT)하고 연결을 닫는 메소드
     */
    public void commit() {
        if (con != null) {
            try {
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }
    }

    /**
     * 트랜잭션을 취소(ROLLBACK)하고 연결을 닫는 메소드
     */
    public void rollback() {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }
    }
    
    // 연결을 닫는 private 헬퍼 메소드
    private void closeConnection() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con = null;
        }
    }
	
    // detail order (수정하기 위함)
 	static OrderDTO detailOrder( int order_id ) {
 		Connection con = null;
 		PreparedStatement pstmt = null;
 		ResultSet rs = null;
 		
 		String detailSql = "SELECT * FROM orders WHERE order_id = ?;";
 		OrderDTO dto = null;
 		try {
 			con = DriverManager.getConnection(url, user, pwd);
 			pstmt = con.prepareStatement(detailSql);
 			
 			pstmt.setInt(1, order_id);
 			rs = pstmt.executeQuery();
 			
 			// rs -> dto 객체로 만들기
 			if (rs.next()) {
 				dto = new OrderDTO();
 				
 				dto.setOrder_id(rs.getInt("order_id"));
 				dto.setCust_name(rs.getString("cust_name"));
 				dto.setMovie_name(rs.getString("movie_name"));
 				dto.setBorrowdate(rs.getDate("borrowdate"));
 				dto.setReturndate(rs.getDate("returndate"));
 				
 			}
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 			DBManager.releaseConnection(rs, pstmt, con);
 		}
 		
 		return dto;
 		
 		
 	}
 	// update order - DATE 만 변경 가능
 	public static int updateOrder(	
 							int order_id,
 							Date borrowdate,
 							Date returndate
 							) {
 		Connection con = null;
 		PreparedStatement pstmt = null;
 		
 		String updateSql = "update orders set borrowdate = ?, returndate = ? where movie_id = ?;";
 		int ret = 0;
 		try {
 			con = DriverManager.getConnection(url, user, pwd);
 			pstmt = con.prepareStatement(updateSql);	// query
 			
 			// 파라미터
 			pstmt.setDate(1, borrowdate);
 			pstmt.setDate(2, returndate);
 			
 			ret = pstmt.executeUpdate();
 			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 			DBManager.releaseConnection(pstmt, con);
 		}
 		
 		return ret ;
 	}
    
    
	// delete Order
	public static int deleteOrder ( int order_id ) {
		
		String deleteSql = "delete from Orders where order_id = ?;";
		
		int ret = -1;
		
		// try-with-resources 블럭 (autoclosable 객체가 생성됨)
		try (
				Connection con = DriverManager.getConnection(url, user, pwd);
				PreparedStatement pstmt = con.prepareStatement(deleteSql);
			){
			
			// 파라미터
			pstmt.setInt(1, order_id);
			
			ret = pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	// list load
	public static List<OrderDTO> listOrder() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String listSql = "SELECT "
				+ "o.order_id, c.cust_id, m.movie_id, m.movie_title, o.saleprice,"
				+ "c.cust_name, c.cust_phone, o.borrowdate, o.returndate "
				+ "FROM orders o JOIN customers c ON o.cust_id = c.cust_id "
				+ 				"JOIN movies m ON m.movie_id = o.movie_id;";
		List<OrderDTO> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(listSql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				OrderDTO dto = new OrderDTO();
				
				dto.setOrder_id(rs.getInt("order_id"));
				dto.setCust_id(rs.getInt("cust_id"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_name(rs.getString("movie_title"));
				dto.setSaleprice(rs.getInt("saleprice"));
				dto.setCust_name(rs.getString("cust_name"));
				dto.setCust_phone(rs.getString("cust_phone"));
				dto.setBorrowdate(rs.getDate("borrowdate"));
				dto.setReturndate(rs.getDate("returndate"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
			
	}
	
	
	
	
}
	
