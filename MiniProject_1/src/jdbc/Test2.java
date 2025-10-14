package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// madang schema 접속
public class Test2 {

	// 접근 주소
	static String url = "jdbc:mysql://localhost:3306/madang";
	static String user = "root";
	static String pwd = "0000";
	public static void main(String[] args) {
		int ret = -1;
		ret = deleteCustomer(6);
		System.out.println(ret);
		
	}
	
	// delete customer by PK
	static int deleteCustomer(int custId) {
				
		String deleteSql = "delete from customer where custid = ? ;";
		
		int ret = -1;
		
		// try with resources 블럭에서 선언, 생성된 AutoClosable 객체는 자도으로 close() 호출된다.
		try (
			Connection con = DriverManager.getConnection(url, user, pwd);	// db 연결
			PreparedStatement pstmt = con.prepareStatement(deleteSql);	// preparedStatement 전달 객체 생성
			
			) {
			// sql 전달 파라미터 (인덱스, value) 설정
			pstmt.setInt(1, custId); 		
			
			ret = pstmt.executeUpdate(); // insert, delete, update 수행 후 결과 출력한 줄 개수 (ex. 1 row affecte 이런거. 0이면 수행 안 된거) 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return ret;
		
	}
	

}
