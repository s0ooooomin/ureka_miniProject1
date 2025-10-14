//package jdbc;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class Main {
//
//	// 접근 주소
//	static String url = "jdbd:mysql://localhost:3306/dvdvdeep";
//	static String user = "root";
//	static String pwd = "0000";
//	
//	public static void main(String[] args) {
//
//		int ret = -1;
//		
//		
//		
//		
//	}
//	
//	// Order
//	// insert, update, delete, return, 
//	static int insertOrder(	int order_id,
//							int cust_id,
//							int movie_id,
//							int saleprice,
//							String borrowdate,
//							String returndate) {
//		
//		int ret = 0;
//		return ret ;
//	}
//
//	
//	
//	
//	
//	
//	
//	// Customers
//	// insert, update, delete
//	static int insertCustomer(	int cust_id,
//								String cust_name,
//								String phone) {
//		Connection con = null;
//		PreparedStatement pstmt = null; // query 전달
//		
//		String insertSql = "insert into customers values (?,?,?);";
//		int ret = -1;
//		
//		try {
//			con = DriverManager.getConnection(url, user, pwd);
//			
//			pstmt = con.prepareStatement(insertSql);
//			
//			// 전달 파라미터
//			pstmt.setInt(1, cust_id);
//			pstmt.setString(2, cust_name);
//			pstmt.setString(3, phone);
//			
//			ret = pstmt.executeUpdate();
//			
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			DBManager.releaseConnection(pstmt, con);
//		}
//		
//		return ret;
//		
//	}
//	
//	
//	
//	
//	
//	// Movies 
//	// insert, update, delete
//	static int insertMovies(int movie_id,
//							String movie_title,
//							String director,
//							int price) {
//		Connection con = null;
//		PreparedStatement pstmt = null; // query 전달
//		
//		String insertSql = "insert into movies values (?,?,?,?);";
//		int ret = -1;
//		
//		try {
//		con = DriverManager.getConnection(url, user, pwd);
//		
//		pstmt = con.prepareStatement(insertSql);
//		
//		// 전달 파라미터
//		pstmt.setInt(1, movie_id);
//		pstmt.setString(2, movie_title);
//		pstmt.setString(3, director);
//		pstmt.setInt(4, price);
//		
//		ret = pstmt.executeUpdate();
//		
//		
//		} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		} finally {
//		DBManager.releaseConnection(pstmt, con);
//		}
//		
//		return ret;
//		
//		}
//	
//}
