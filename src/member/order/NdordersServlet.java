package member.order;

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
import shop.shopDTO.ShopDTO;
import util.DBConnection;
import util.MyServlet;


@WebServlet("/nd.orders")
public class NdordersServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		String SQL = "SELECT COMPANY_CODE FROM ACCOUNT WHERE ACCOUNT_ID=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int company_code = rs.getInt("company_code");
			
			SQL = "SELECT MEMBER_CODE FROM MEMBER WHERE ACCOUNT_ID=?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int member_code = rs.getInt("member_code");
			
			
			String shoplist = "SELECT * FROM SHOP WHERE COMPANY_CODE=? AND MEMBER_CODE=? ORDER BY SHOP_CODE";
			ps = conn.prepareStatement(shoplist);
			ps.setInt(1, company_code);
			ps.setInt(2, member_code);
			rs = ps.executeQuery();
			List<ShopDTO> shop = new ArrayList<>();   // 해당 직원이 관리하는 점포 리스트
			while(rs.next()) {
				ShopDTO shopdto = new ShopDTO();
				shopdto.setShop_code(rs.getInt("shop_code"));
				shopdto.setCompany_code(rs.getInt("company_code"));
				shopdto.setMember_code(rs.getInt("member_code"));
				shopdto.setShop_name(rs.getString("shop_name"));
				shopdto.setAccount_id(rs.getString("account_id"));
				shop.add(shopdto);
			}
			List<OrdersDTO> orders = new ArrayList<>();
			for(int i=0;i<shop.size();i++) {
				String orderslist = "SELECT * FROM ORDERS "
						+ "WHERE SHOP_CODE=? AND ORDERS_SORT=0 "
						+ "ORDER BY ORDERS_CODE DESC";
				ps = conn.prepareStatement(orderslist);
				ps.setInt(1, shop.get(i).getShop_code());
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
		
		super.forward(req, resp, "/WEB-INF/views/order/ndorders.jsp");
	}

}
