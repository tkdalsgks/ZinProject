package dto;

public class SitemDTO {
	int sitem_code;
	int shop_code;
	int item_code;
	int sitem_amount;
	
	public SitemDTO() {
		
	}
	
	public SitemDTO(int sitem_code, int shop_code, int item_code, int sitem_amount) {
		super();
		this.sitem_code = sitem_code;
		this.shop_code = shop_code;
		this.item_code = item_code;
		this.sitem_amount = sitem_amount;
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
