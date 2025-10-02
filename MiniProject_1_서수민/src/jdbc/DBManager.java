package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

	// 접근 주소
	static String url = "jdbc:mysql://localhost:3306/dvdvdeep";
	static String user = "root";
	static String pwd = "0000";
	
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
		
	}
	
	// 두개받음 (query문만)
	public static void releaseConnection(PreparedStatement pstmt, Connection con) {
		
	}
	
	// 세개받음 (읽어서 출력)
	public static void releaseConnection(ResultSet rs, PreparedStatement pstmt, Connection con) {
		
	}
}
