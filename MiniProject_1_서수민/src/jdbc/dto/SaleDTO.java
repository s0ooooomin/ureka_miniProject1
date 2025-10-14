package jdbc.dto;

import java.sql.Date;

public class SaleDTO {

	private int sale_id;
	private int movie_id;
	private int saleprice;
	private Date returndate;
	
	
	public int getSale_id() {
		return sale_id;
	}
	public void setSale_id(int sale_id) {
		this.sale_id = sale_id;
	}
	
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	
	public int getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}
	
	public Date getReturndate() {
		return returndate;
	}
	public void setReturndate(Date returndate) {
		this.returndate = returndate;
	}
	
	
	@Override
	public String toString() {
		return "SaleDTO [sale_id=" + sale_id + ", movie_id=" + movie_id + ", saleprice=" + saleprice + ", returndate="
				+ returndate + "]";
	}
	
}
