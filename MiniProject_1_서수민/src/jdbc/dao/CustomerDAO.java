package jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.DBManager;
import jdbc.dto.CustomerDTO;

public class CustomerDAO {

	// 접근 주소
	static String url = "jdbc:mysql://localhost:3306/dvdvdeep";
	static String user = "root";
	static String pwd = "0000";
	
	public static void main(String[] args) {

		int ret = -1;
		
//		ret = insertCustomer(1, "서수민", "010-6660-0166");
//		ret = updateCustomer(1, "010-6661-0166");
		ret = deleteCustomer(1);
		
		System.out.println(ret);
		
	}
		
	// Customers
	// 1. insert
	public static int insertCustomer(	String cust_name,
								String phone) {
		Connection con = null;
		PreparedStatement pstmt = null; // query 전달
		
		String insertSql = "insert into customers values ( default ,?,?);";
		int ret = -1;
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(insertSql);
			
			// 전달 파라미터
			pstmt.setString(1, cust_name);
			pstmt.setString(2, phone);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
		
	}
	
	// 2-1. detail Customer (수정하기 위함)
	static CustomerDTO detailCustomer( int cust_id ) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String detailSql = "SELECT * FROM customers WHERE cust_id = ?;";
		CustomerDTO dto = null;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(detailSql);
			
			pstmt.setInt(1, cust_id);
			rs = pstmt.executeQuery();
			
			// rs -> dto 객체로 만들기
			if (rs.next()) {
				dto = new CustomerDTO();
				
				dto.setCust_id(rs.getInt("cust_id"));
				dto.setCust_name(rs.getString("cust_name"));
				dto.setCust_phone(rs.getString("cust_phone"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return dto;
		
		
	}
	// 2-2. update Customer - phone 만 변경 가능
	public static int updateCustomer(	
							int cust_id,
							String phone
							) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String updateSql = "update customers set cust_phone = ? where cust_id = ?;";
		int ret = 0;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(updateSql);	// query
			
			// 파라미터
			pstmt.setString(1, phone);
			pstmt.setInt(2, cust_id);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret ;
	}
	
	// delete customer
	public static int deleteCustomer( int cust_id ) {
		
		String deleteSql = "delete from Customers where cust_id = ?;";
		
		int ret = -1;
		
		// try-with-resources 블럭 (autoclosable 객체가 생성됨)
		try (
				Connection con = DriverManager.getConnection(url, user, pwd);
				PreparedStatement pstmt = con.prepareStatement(deleteSql);
			){
			
			// 파라미터
			pstmt.setInt(1, cust_id);
			
			ret = pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	// list 불러오기
	public static List<CustomerDTO> listCustomer() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String listSql = "SELECT * FROM customers;";
		List<CustomerDTO> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(listSql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				CustomerDTO dto = new CustomerDTO();
				
				dto.setCust_id(rs.getInt("cust_id"));
				dto.setCust_name(rs.getString("cust_name"));
				dto.setCust_phone(rs.getString("cust_phone"));
				
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
