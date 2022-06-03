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

@WebServlet("/a.orders")
public class AordersServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			String shopCode = "SELECT SHOP_CODE FROM SHOP WHERE ACCOUNT_ID=?";
			
			ps = conn.prepareStatement(shopCode);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int shop_code = rs.getInt("shop_code");
			
			String orderslist = "SELECT * FROM ORDERS WHERE SHOP_CODE=? AND ORDERS_AMOUNT=ORDERS_CAMOUNT";
			
			List<OrdersDTO> orders = new ArrayList<>();
			
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
			}
			req.setAttribute("orderslist", orders);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/order/aorders.jsp");
	}

}
