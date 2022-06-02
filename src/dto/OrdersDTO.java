package dto;

import java.sql.Date;

public class OrdersDTO {
	int orders_code;
	int shop_code;
	int item_code;
	int orders_amount;
	int orders_camount;
	Date orders_date;
	int orders_sort;
	
	
	public OrdersDTO() {
		
	}
	
	public OrdersDTO(int orders_code, int shop_code, int item_code, int orders_amount, int orders_camount,
			Date orders_date, int orders_sort) {
		super();
		this.orders_code = orders_code;
		this.shop_code = shop_code;
		this.item_code = item_code;
		this.orders_amount = orders_amount;
		this.orders_camount = orders_camount;
		this.orders_date = orders_date;
		this.orders_sort = orders_sort;
	}

	public int getOrders_code() {
		return orders_code;
	}
	
	public void setOrders_code(int orders_code) {
		this.orders_code = orders_code;
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
	
	public int getOrders_amount() {
		return orders_amount;
	}
	
	public void setOrders_amount(int orders_amount) {
		this.orders_amount = orders_amount;
	}
	
	public int getOrders_camount() {
		return orders_camount;
	}
	
	public void setOrders_camount(int orders_camount) {
		this.orders_camount = orders_camount;
	}
	
	public Date getOrders_date() {
		return orders_date;
	}
	public void setOrders_date(Date orders_date) {
		this.orders_date = orders_date;
	}
	
	public int getOrders_sort() {
		return orders_sort;
	}
	
	public void setOrders_sort(int orders_sort) {
		this.orders_sort = orders_sort;
	}
	
	
}
