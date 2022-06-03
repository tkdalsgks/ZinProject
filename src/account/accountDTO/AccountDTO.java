package account.accountDTO;

public class AccountDTO {
	String account_id;
	String account_pwd;
	String member_name;
	String shop_name;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(String account_id, String account_pwd, String member_name, String shop_name) {
		super();
		this.account_id = account_id;
		this.account_pwd = account_pwd;
		this.member_name = member_name;
		this.shop_name = shop_name;
	}

	public String getAccount_id() {
		return account_id;
	}
	
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	
	public String getAccount_pwd() {
		return account_pwd;
	}
	
	public void setAccount_pwd(String account_pwd) {
		this.account_pwd = account_pwd;
	}
	
	public String getMember_name() {
		return member_name;
	}
	
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	
	public String getShop_name() {
		return shop_name;
	}
	
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	
	
}
