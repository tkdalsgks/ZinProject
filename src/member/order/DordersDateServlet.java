package member.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

@WebServlet("/dorders_date")
public class DordersDateServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date start_date = Date.valueOf(req.getParameter("start_date"));
			Date end_date = Date.valueOf(req.getParameter("end_date"));
			
			String SQL = "SELECT COMPANY_CODE FROM ACCOUNT WHERE ACCOUNT_ID=?";
			PreparedStatement ps = null;
			ResultSet rs = null;
			req.setCharacterEncoding("UTF-8");
			
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
			List<ShopDTO> shop = new ArrayList<>();   // ?????? ????????? ???????????? ?????? ?????????
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
						+ "WHERE SHOP_CODE=? AND ORDERS_SORT=1 AND ORDERS_DATE>=? AND ORDERS_DATE<=to_date(?)+1"
						+ "ORDER BY ORDERS_CODE DESC";
				ps = conn.prepareStatement(orderslist);
				ps.setInt(1, shop.get(i).getShop_code());
				ps.setDate(2, start_date);
				ps.setDate(3,end_date);
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
			
			
		} catch (SQLException e) {
			System.out.println("DB ?????? ???????????? SQL ?????? ??????");
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/order/dorders.jsp");
		
	}

}
