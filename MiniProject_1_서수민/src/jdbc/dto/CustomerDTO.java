package jdbc.dto;

public class CustomerDTO {

	private int cust_id;
	private String cust_name;
	private String cust_phone;
	
	
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
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
	
	
	@Override
	public String toString() {
		return "CustmoerDto [cust_id=" + cust_id + ", cust_name=" + cust_name + ", cust_phone=" + cust_phone + "]";
	}
	
}
