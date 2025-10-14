package jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.DBManager;
import jdbc.dto.SaleDTO;

public class SaleDAO {

	// 접근 주소
	static String url = "jdbc:mysql://localhost:3306/dvdvdeep";
	static String user = "root";
	static String pwd = "0000";

	
	// list load
	public static List<SaleDTO> listSale() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String listSql = "SELECT * FROM sales;";
		List<SaleDTO> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(listSql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				SaleDTO dto = new SaleDTO();
				
				dto.setSale_id(rs.getInt("sale_id"));
				dto.setMovie_id(rs.getInt("movie_id"));
				dto.setSaleprice(rs.getInt("saleprice"));
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
	
