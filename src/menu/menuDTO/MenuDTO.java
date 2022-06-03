package menu.menuDTO;

public class MenuDTO {
	int menu_code;
	int company_code;
	String menu_name;
	int menu_top;
	String menu_url;
	String menu_access;
	
	
	public MenuDTO() {
		
	}
	
	public MenuDTO(int menu_code, int company_code, String menu_name, int menu_top, String menu_url,
			String menu_access) {
		super();
		this.menu_code = menu_code;
		this.company_code = company_code;
		this.menu_name = menu_name;
		this.menu_top = menu_top;
		this.menu_url = menu_url;
		this.menu_access = menu_access;
	}
	
	public int getMenu_code() {
		return menu_code;
	}
	public void setMenu_code(int menu_code) {
		this.menu_code = menu_code;
	}
	
	public int getCompany_code() {
		return company_code;
	}
	public void setCompany_code(int company_code) {
		this.company_code = company_code;
	}
	
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	
	public int getMenu_top() {
		return menu_top;
	}
	public void setMenu_top(int menu_top) {
		this.menu_top = menu_top;
	}
	
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	
	public String getMenu_access() {
		return menu_access;
	}
	public void setMenu_access(String menu_access) {
		this.menu_access = menu_access;
	}
	
	
}
