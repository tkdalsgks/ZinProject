package shop.item.itemDTO;

public class ItemDTO {
	int item_code;
	int company_code;
	String item_name;
	int item_price;
	int item_sort;
	
	public ItemDTO() {}
	
	public ItemDTO(int item_code, int company_code, String item_name, int item_price) {
		super();
		this.item_code = item_code;
		this.company_code = company_code;
		this.item_name = item_name;
		this.item_price = item_price;
	}
	
	
	
	public int getItem_sort() {
		return item_sort;
	}

	public void setItem_sort(int item_sort) {
		this.item_sort = item_sort;
	}

	public int getItem_code() {
		return item_code;
	}
	public void setItem_code(int item_code) {
		this.item_code = item_code;
	}
	
	public int getCompany_code() {
		return company_code;
	}
	public void setCompany_code(int company_code) {
		this.company_code = company_code;
	}
	
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
	public int getItem_price() {
		return item_price;
	}
	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}
	
	
}
