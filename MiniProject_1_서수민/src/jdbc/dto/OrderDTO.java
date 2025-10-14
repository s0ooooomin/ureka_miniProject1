package jdbc.dto;

import java.sql.Date;

public class OrderDTO {

	private int order_id;
	private int cust_id;
	private int movie_id;
	private String movie_name;
	private int saleprice;
	private String cust_name;
	private String cust_phone;
	private Date borrowdate;
	private Date returndate;
	
//	OrderDTO ( String cust_name, String cust_phone, String movie_name, String borrowdate ){
//		super();
//		this.cust_name = cust_name;
//		this.cust_phone = cust_phone;
//		this.movie_name = movie_name;
//		this.borrowdate = borrowdate;
//	}
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	
	public String getMovie_name() {
		return movie_name;
	}
	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public int getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCust_phone() {
		return cust_phone;
	}
	public void setCust_phone(String cust_phone) {
		this.cust_phone = cust_phone;
	}

	public Date getBorrowdate() {
		return borrowdate;
	}
	public void setBorrowdate(Date borrowdate) {
		this.borrowdate = borrowdate;
	}

	public Date getReturndate() {
		return returndate;
	}
	public void setReturndate(Date returndate) {
		this.returndate = returndate;
	}
	
	
	@Override
	public String toString() {
		return "OrderDTO [order_id=" + order_id + ", cust_id=" + cust_id + ", movie_id=" + movie_id + ", saleprice="
				+ saleprice + ", cust_name=" + cust_name + ", cust_phone=" + cust_phone + ", borrowdate=" + borrowdate
				+ ", returndate=" + returndate + "]";
	}

	
}
