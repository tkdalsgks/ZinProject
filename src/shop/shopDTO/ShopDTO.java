package shop.shopDTO;

public class ShopDTO {
	int shop_code;
	int company_code;
	int member_code;
	String shop_name;
	String account_id;
	
	public ShopDTO() {}
	
	public ShopDTO(int shop_code, int company_code, int member_code, String shop_name, String account_id) {
		super();
		this.shop_code = shop_code;
		this.company_code = company_code;
		this.member_code = member_code;
		this.shop_name = shop_name;
		this.account_id = account_id;
	}

	public int getShop_code() {
		return shop_code;
	}
	
	public void setShop_code(int shop_code) {
		this.shop_code = shop_code;
	}
	
	public int getCompany_code() {
		return company_code;
	}
	
	public void setCompany_code(int company_code) {
		this.company_code = company_code;
	}
	
	public int getMember_code() {
		return member_code;
	}
	
	public void setMember_code(int member_code) {
		this.member_code = member_code;
	}
	
	public String getShop_name() {
		return shop_name;
	}
	
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	public String getAccount_id() {
		return account_id;
	}
	
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	

}
