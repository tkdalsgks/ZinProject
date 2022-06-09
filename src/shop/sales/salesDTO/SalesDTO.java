package shop.sales.salesDTO;

import java.sql.Date;

public class SalesDTO {
	int sales_code;
	int sales_number;
	int sitem_code;
	int pay_code;
	int sales_amount;
	int sales_price;
	int sales_sort;
	Date sales_date;
	String item_name;
	int item_code;
	
	public SalesDTO() {
		
	}
	
	public SalesDTO(int sales_code, int sales_number, int sitem_code, int pay_code, int sales_amount, int sales_price,
			int sales_sort, Date sales_date) {
		super();
		this.sales_code = sales_code;
		this.sales_number = sales_number;
		this.sitem_code = sitem_code;
		this.pay_code = pay_code;
		this.sales_amount = sales_amount;
		this.sales_price = sales_price;
		this.sales_sort = sales_sort;
		this.sales_date = sales_date;
	}
	
	
	
	public int getItem_code() {
		return item_code;
	}

	public void setItem_code(int item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public int getSales_code() {
		return sales_code;
	}
	
	public void setSales_code(int sales_code) {
		this.sales_code = sales_code;
	}
	
	public int getSales_number() {
		return sales_number;
	}
	
	public void setSales_number(int sales_number) {
		this.sales_number = sales_number;
	}
	
	public int getSitem_code() {
		return sitem_code;
	}
	
	public void setSitem_code(int sitem_code) {
		this.sitem_code = sitem_code;
	}
	
	public int getPay_code() {
		return pay_code;
	}
	
	public void setPay_code(int pay_code) {
		this.pay_code = pay_code;
	}
	
	public int getSales_amount() {
		return sales_amount;
	}
	
	public void setSales_amount(int sales_amount) {
		this.sales_amount = sales_amount;
	}
	
	public int getSales_price() {
		return sales_price;
	}
	
	public void setSales_price(int sales_price) {
		this.sales_price = sales_price;
	}
	
	public int getSales_sort() {
		return sales_sort;
	}
	
	public void setSales_sort(int sales_sort) {
		this.sales_sort = sales_sort;
	}
	
	public Date getSales_date() {
		return sales_date;
	}
	
	public void setSales_date(Date sales_date) {
		this.sales_date = sales_date;
	}
	
	
}


