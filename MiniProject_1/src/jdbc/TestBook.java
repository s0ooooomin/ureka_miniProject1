package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestBook {
	
	// 접근주소
	static String url = "jdbc:mysql://localhost:3306/madang";
	static String user = "root";
	static String pwd = "0000"; 

	public static void main(String[] args) {
		
		int ret = -1;
//		ret = insertBook(11, "손흥민", "LAFC", 15000);
//		ret = updateBook(11, "손흥민2", "LAFC2", 15002);
//		ret = deleteBook(11);
//		
//		CustomerDto dto = detailCustomer(1);
//		System.out.println(dto);	// detailCustomer
		
//		List<CustomerDto> list = listCustomer();
//		for (CustomerDto dto : list) {
//			System.out.println(dto);
//		}
		
		System.out.println(ret);
		
		
	}
	
	// insert, update, delete, detail, list
	static int insertBook(int bookid, String bookname, String publisher, int price) {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null;	// query 전달
		
		String insertSql = "insert into book values (?, ?, ?, ?) ;";	// query문 입력
		int ret = -1;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			
			pstmt = con.prepareStatement(insertSql);	// db로 전달할 객체 생성
			// sql 전달 파라미터 (인덱스, value)
			pstmt.setInt(1, bookid);
			pstmt.setString(2, bookname);
			pstmt.setString(3, publisher);
			pstmt.setInt(4, price);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 작업 종료 후 실행됨
			// 리소스 정리 작업
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return ret;
		
	}
	
	// update, delete, detail, list
	static int updateBook(int bookid, String bookname, String publisher, int price) {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null;	// query 전달
		
		String updateSql = "update book set bookname = ?, publisher = ?, price = ? where bookid = ?;";	// query문 입력
		int ret = -1;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			
			pstmt = con.prepareStatement(updateSql);	// db로 전달할 객체 생성
			// sql 전달 파라미터 (인덱스, value)

			pstmt.setString(1, bookname);
			pstmt.setString(2, publisher);
			pstmt.setInt(3, price);
			pstmt.setInt(4, bookid);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 작업 종료 후 실행됨
			// 리소스 정리 작업
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return ret;
		
	}
	
	// delete, detail, list
	static int deleteBook(int bookid) {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null;	// query 전달
		
		String deleteSql = "delete from book where bookid = ? ;";	// query문 입력
		int ret = -1;
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(deleteSql);	// db로 전달할 객체 생성
			
			// sql 전달 파라미터 (인덱스, value) -> query문에 대입됨
			pstmt.setInt(1, bookid);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 작업 종료 후 실행됨
			// 리소스 정리 작업
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return ret;
		
	}

	// detail, list
	static BookDto detailBook(int bookid) {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null;	// query 전달
		ResultSet rs= null;
		
		String detailSql = "select * from book where bookid = ? ;";	// query문 입력
		BookDto dto = null;
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(detailSql);	// db로 전달할 객체 생성
			
			// sql 전달 파라미터 (인덱스, value) -> query문에 대입됨
			pstmt.setInt(1, bookid);
			
			rs = pstmt.executeQuery();
			
			// rs -> dto 객체로 만들기
			if ( rs.next() ) {
				dto = new BookDto();
				dto.setBookid(rs.getInt("bookid"));
				dto.setBookname(rs.getString("bookname"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setPrice(rs.getInt("price"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 작업 종료 후 실행됨
			// 리소스 정리 작업
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return dto;
		
	}
	
	// list
	static List<BookDto> listBook() {
		
		Connection con = null;			// 연결
		PreparedStatement pstmt = null;	// query 전달
		ResultSet rs= null;
		
		String listSql = "select * from book;";	// query문 입력
		List<BookDto> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(listSql);	// db로 전달할 객체 생성
			
			// sql 전달 파라미터 (인덱스, value) -> query문에 대입됨
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BookDto dto = new BookDto();
				
				dto.setBookid(rs.getInt("bookid"));
				dto.setBookname(rs.getString("bookname"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setPrice(rs.getInt("price"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 작업 종료 후 실행됨
			// 리소스 정리 작업
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return list;
		
	}

}
