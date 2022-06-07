package shop.sitem.sitemDTO;

public class SitemDTO {
	int sitem_code;
	int shop_code;
	int item_code;
	int sitem_amount;
	String item_name;
	
	public SitemDTO() {
		
	}
	
	public SitemDTO(int sitem_code, int shop_code, int item_code, int sitem_amount,String item_name) {
		super();
		this.sitem_code = sitem_code;
		this.shop_code = shop_code;
		this.item_code = item_code;
		this.sitem_amount = sitem_amount;
		this.item_name = item_name;
	}
	
	

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getSitem_code() {
		return sitem_code;
	}
	
	public void setSitem_code(int sitem_code) {
		this.sitem_code = sitem_code;
	}
	
	public int getShop_code() {
		return shop_code;
	}
	
	public void setShop_code(int shop_code) {
		this.shop_code = shop_code;
	}
	
	public int getItem_code() {
		return item_code;
	}
	
	public void setItem_code(int item_code) {
		this.item_code = item_code;
	}
	
	public int getSitem_amount() {
		return sitem_amount;
	}
	
	public void setSitem_amount(int sitem_amount) {
		this.sitem_amount = sitem_amount;
	}
	
	
}
