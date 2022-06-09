package member.sales.payDTO;

public class PayDTO {
	int pay_code;
	int company_code;
	String pay_name;
	
	public PayDTO() {
		
	}
	
	public PayDTO(int pay_code, int company_code, String pay_name) {
		super();
		this.pay_code = pay_code;
		this.company_code = company_code;
		this.pay_name = pay_name;
	}
	
	public int getPay_code() {
		return pay_code;
	}
	
	public void setPay_code(int pay_code) {
		this.pay_code = pay_code;
	}
	
	public int getCompany_code() {
		return company_code;
	}
	
	public void setCompany_code(int company_code) {
		this.company_code = company_code;
	}
	
	public String getPay_name() {
		return pay_name;
	}
	
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	
	
}
