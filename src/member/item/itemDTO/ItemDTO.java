package member.item.itemDTO;

public class ItemDTO {
	int item_code;
	int company_code;
	String item_name;
	int item_price;
	int sitem_amount;
	int shop_code;
	int item_sort;
	int psales_amount;
	int sitem_code;
	
	public ItemDTO() {}
	
	public ItemDTO(int item_code, int company_code, String item_name, int item_price) {
		super();
		this.item_code = item_code;
		this.company_code = company_code;
		this.item_name = item_name;
		this.item_price = item_price;
	}
	
	public ItemDTO(int item_code, int company_code, int shop_code, String item_name, int item_price, int sitem_amount, int sitem_code) {
		super();
		this.item_code = item_code;
		this.company_code = company_code;
		this.shop_code = shop_code;
		this.item_name = item_name;
		this.item_price = item_price;
		this.sitem_amount = sitem_amount;
		this.sitem_code = sitem_code;
	}
	
	public ItemDTO(String item_name, int item_price, int psales_amount) {
		super();
		this.item_name = item_name;
		this.item_price = item_price;
		this.psales_amount = psales_amount;
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

	public int getSitem_amount() {
		return sitem_amount;
	}

	public void setSitem_amount(int sitem_amount) {
		this.sitem_amount = sitem_amount;
	}

	public int getShop_code() {
		return shop_code;
	}

	public void setShop_code(int shop_code) {
		this.shop_code = shop_code;
	}

	public int getPsales_amount() {
		return psales_amount;
	}

	public void setPsales_amount(int psales_amount) {
		this.psales_amount = psales_amount;
	}

	public int getSitem_code() {
		return sitem_code;
	}

	public void setSitem_code(int sitem_code) {
		this.sitem_code = sitem_code;
	}
	
}
