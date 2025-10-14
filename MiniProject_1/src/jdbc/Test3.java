package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// madang schema 접속
public class Test3 {
	// 접근 주소
		static String url = "jdbc:mysql://localhost:3306/madang";
		static String user = "root";
		static String pwd = "0000";

	public static void main(String[] args) {
		int ret = -1;
		ret = deleteCustomer(6);
		System.out.println(ret);
		
	}
	
	// insert customer
	static int insertCustomer(int custId, String name, String address, String phone) {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null; // query 전달
		
		String insertSql = "insert into customer values (?, ?, ?, ?);";
		
		int ret = -1;
		try {
			con = DBManager.getConnection();	// db 연결
			pstmt = con.prepareStatement(insertSql);	// preparedStatement 전달 객체 생성
			
			// sql 전달 파라미터 (인덱스, value) 설정
			pstmt.setInt(1, custId); 		// 1번째 물음표에 custId
			pstmt.setString(2, name); 		// 2번째 물음표에 name
			pstmt.setString(3, address);	// 3번째 물음표에 address
			pstmt.setString(4, phone); 		// 4번째 물음표에 phone
			
			ret = pstmt.executeUpdate(); // insert, delete, update 수행 후 결과 출력한 줄 개수 (ex. 1 row affecte 이런거. 0이면 수행 안 된거) 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 이제 db 종료
			// 리소스 정리 작업
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
		
	}
	
	// update customer
	static int updateCustomer(int custId, String name, String address, String phone) {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null; // query 전달
		
		String updateSql = "update customer set name = ?, address = ?, phone = ? where custid = ? ;";
		
		int ret = -1;
		
		try {
			con = DBManager.getConnection();	// db 연결
			pstmt = con.prepareStatement(updateSql);	// preparedStatement 전달 객체 생성
			
			// sql 전달 파라미터 (인덱스, value) 설정
			pstmt.setString(1, name); 	
			pstmt.setString(2, address); 
			pstmt.setString(3, phone);	
			pstmt.setInt(4, custId); 		
			
			ret = pstmt.executeUpdate(); // insert, delete, update 수행 후 결과 출력한 줄 개수 (ex. 1 row affecte 이런거. 0이면 수행 안 된거) 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 이제 db 종료
			// 리소스 정리 작업
			DBManager.releaseConnection(pstmt, con);

		}
		
		return ret;
		
	}
	
	// delete customer by PK
	static int deleteCustomer(int custId) {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null; // query 전달
		
		String deleteSql = "delete from customer where custid = ? ;";
		
		int ret = -1;
		
		try {
			con = DBManager.getConnection();	// db 연결
			pstmt = con.prepareStatement(deleteSql);	// preparedStatement 전달 객체 생성
			
			// sql 전달 파라미터 (인덱스, value) 설정
			pstmt.setInt(1, custId); 		
			
			ret = pstmt.executeUpdate(); // insert, delete, update 수행 후 결과 출력한 줄 개수 (ex. 1 row affecte 이런거. 0이면 수행 안 된거) 
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 이제 db 종료
			// 리소스 정리 작업
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
		
	}
	
	// customer 1건 조회 by PK
	static CustomerDto detailCustomer(int custId) {
		Connection con = null;			// 연결
		PreparedStatement pstmt = null; // query 전달
		ResultSet rs = null;
		
		String detailSql = "select * from customer where custid = ? ;";
		CustomerDto dto = null;
		
		try {
			con = DBManager.getConnection();	// db 연결
			
			pstmt = con.prepareStatement(detailSql);	// preparedStatement 전달 객체 생성
			
			pstmt.setInt(1, custId);		// sql 전달 파라미터 (인덱스, value) 설정 		
			
			rs = pstmt.executeQuery(); 	// select : executeQuery
			
			// rs -> CustomerDto 객체로 만들기
			if ( rs.next() ) {	// select by PK 이므로, if
				dto = new CustomerDto();
				dto.setCustId(rs.getInt("custid")); // 이런 이름을 가진 컬럼의 값 get
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 이제 db 종료
			// 리소스 정리 작업
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return dto;
	}
	
	// customer 전체(여러건) 조회 by PK
	static List<CustomerDto> listCustomer() {
		Connection con = null;			// 연결
		PreparedStatement pstmt = null; // query 전달
		ResultSet rs = null;
		
		String listSql = "select * from customer;";	// 전달 파라미터X (setInt 이런거X)
		List<CustomerDto> list = new ArrayList<>(); // 아직 empty
		
		try {
			con = DBManager.getConnection();	// db 연결
			
			pstmt = con.prepareStatement(listSql);	// preparedStatement 전달 객체 생성
						
			rs = pstmt.executeQuery(); 	// select : executeQuery
			
			// rs -> List<CustomerDto> 로 완성하기 (1개의 if문으로)
			while ( rs.next() ) {	// select 여러건
			
				CustomerDto dto = new CustomerDto();	// row 1건마다 개별 CustomerDto 객체 생성
				
				dto.setCustId(rs.getInt("custid"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 이제 db 종료
			// 리소스 정리 작업
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	

}
