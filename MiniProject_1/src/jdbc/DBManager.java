package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	// 접근 주소
	static String url = "jdbc:mysql://localhost:3306/madang";
	static String user = "root";
	static String pwd = "0000";
	
	public static Connection getConnection () {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, user, pwd);	// db 연결
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return con;
	}
	
	// 두개받음
	public static void releaseConnection(PreparedStatement pstmt, Connection con) {
		try {
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 세개받음
	public static void releaseConnection(ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
