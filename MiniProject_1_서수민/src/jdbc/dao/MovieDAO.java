package jdbc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.DBManager;
import jdbc.dto.MovieDTO;

public class MovieDAO {

	// 접근 주소
	static String url = "jdbc:mysql://localhost:3306/dvdvdeep";
	static String user = "root";
	static String pwd = "0000";
	
	public static void main(String[] args) {

		int ret = -1;
		
		System.out.println(ret);
		
	}
	
	// insert
	public static int insertMovie(	
							String movie_title,
							String director,
							int price
							) {
				
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String insertSql = "insert into movies values (default, ?, ?, ?);";
		int ret = 0;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(insertSql);	// query
			
			// 파라미터
			pstmt.setString(1,movie_title);
			pstmt.setString(2,director);
			pstmt.setInt(3, price);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret ;
	}
	// detail movie (수정하기 위함)
	static MovieDTO detailMovie( int movie_id ) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String detailSql = "SELECT * FROM movies WHERE movie_id = ?;";
		MovieDTO dto = null;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(detailSql);
			
			pstmt.setInt(1, movie_id);
			rs = pstmt.executeQuery();
			
			// rs -> dto 객체로 만들기
			if (rs.next()) {
				dto = new MovieDTO();
				
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_title(rs.getString("movie_title"));
				dto.setDirector(rs.getString("director"));
				dto.setPrice(rs.getInt("price"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return dto;
		
		
	}
	// update movie - price 만 변경 가능
	public static int updateMovie(	
							int movie_id,
							int price
							) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String updateSql = "update movies set price = ? where movie_id = ?;";
		int ret = 0;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(updateSql);	// query
			
			// 파라미터
			pstmt.setInt(1, price);
			pstmt.setInt(2, movie_id);
			
			ret = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret ;
	}
	
	
	// delete Movie
	public static int deleteMovie( int movie_id ) {
		
		String deleteSql = "delete from movies where movie_id = ?;";
		
		int ret = -1;
		
		// try-with-resources 블럭 (autoclosable 객체가 생성됨)
		try (
				Connection con = DriverManager.getConnection(url, user, pwd);
				PreparedStatement pstmt = con.prepareStatement(deleteSql);
			){
			
			// 파라미터
			pstmt.setInt(1, movie_id);
			
			ret = pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	// borrow
	// 입력받은 phone이 이미 있다면 insert order 만 진행
	// 입력받은 phone이 없다면 customer insert + order insert	
	public static int borrowMovie(String cust_name, String cust_phone, int movie_id) {
        // 1. DB에 연결하고 트랜잭션을 시작합니다.
		Connection con = null;
 		CallableStatement cstmt = null;
 		
 		int ret = -1;
        // 2. 프로시저 수행
        String procedureSql = "CALL Order_Movie(?,?,?);";
        try {
 			con = DriverManager.getConnection(url, user, pwd);
 			cstmt = con.prepareCall(procedureSql);	// query
 			
 			// 파라미터
 			cstmt.setString(1, cust_name);
 			cstmt.setString(2, cust_phone);
 			cstmt.setInt(3, movie_id);
 			
 			ret = cstmt.executeUpdate();
 			
 		} catch (SQLException e) {
 			e.printStackTrace();
 		} finally {
 			try {
 	            if (cstmt != null) cstmt.close();
 	            if (con != null) con.close();
 	        } catch (SQLException e) {
 	            e.printStackTrace();
 	        }
 		}
        
        return ret;
        
    }
		
		
	
	// list 불러오기
	public static List<MovieDTO> listMovie() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String listSql = "SELECT * FROM movies;";
		List<MovieDTO> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(listSql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_title(rs.getString("movie_title"));
				dto.setDirector(rs.getString("director"));
				dto.setPrice(rs.getInt("price"));
				
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
	// 검색 시 list
	public static List<MovieDTO> listMovie(String searchWord) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String listSql = "SELECT * FROM movies"
				+ " WHERE movie_id LIKE ?"
				+ " OR movie_title LIKE ?"
				+ " OR director LIKE ?"
				+ " OR price LIKE ?;";
		List<MovieDTO> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(listSql);
			// 파라미터
			pstmt.setString(1, "%" + searchWord + "%");
			pstmt.setString(2, "%" + searchWord + "%");
			pstmt.setString(3, "%" + searchWord + "%");
			pstmt.setString(4, "%" + searchWord + "%");

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				MovieDTO dto = new MovieDTO();
				
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setMovie_title(rs.getString("movie_title"));
				dto.setDirector(rs.getString("director"));
				dto.setPrice(rs.getInt("price"));
				
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
