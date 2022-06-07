package shop.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.order.orderDTO.OrdersDTO;
import util.DBConnection;
import util.MyServlet;

@WebServlet("/o.orders")
public class OordersServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			String shopCode = "SELECT SHOP_CODE FROM SHOP WHERE ACCOUNT_ID=?";
			
			ps = conn.prepareStatement(shopCode);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int shop_code = rs.getInt("shop_code");
			
			String orderslist = "SELECT * FROM ORDERS WHERE SHOP_CODE=? ORDER BY ORDERS_CODE DESC";
			
			List<OrdersDTO> orders = new ArrayList<>();
			List<String> itemname = new ArrayList<>();
			
			ps = conn.prepareStatement(orderslist);
			ps.setInt(1, shop_code);
			rs = ps.executeQuery();
			while(rs.next()) {
				OrdersDTO ordersdto = new OrdersDTO();
				ordersdto.setOrders_code(rs.getInt("orders_code"));
				ordersdto.setShop_code(rs.getInt("shop_code"));
				ordersdto.setItem_code(rs.getInt("item_code"));
				ordersdto.setOrders_amount(rs.getInt("orders_amount"));
				ordersdto.setOrders_camount(rs.getInt("orders_camount"));
				ordersdto.setOrders_date(rs.getDate("orders_date"));
				ordersdto.setOrders_sort(rs.getInt("orders_sort"));
				orders.add(ordersdto);
				
				String itemName = "SELECT ITEM_NAME FROM ITEM WHERE ITEM_CODE=?";
				ps1 = conn.prepareStatement(itemName);
				ps1.setInt(1,ordersdto.getItem_code());
				
				rs1 = ps1.executeQuery();
				rs1.next();
				String item_name = rs1.getString("item_name");
				itemname.add(item_name);
				
			}
			req.setAttribute("orderslist", orders);
			req.setAttribute("itemname", itemname);
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/order/oorders.jsp");
	}

}
