package shop.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;
import util.MyServlet;

@WebServlet("/p.orders")
public class OrderServlet extends MyServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("ordermsg");
		super.forward(req, resp, "/WEB-INF/views/order/order.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String ordermsg = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		String account_id = (String)session.getAttribute("account_id");
		//int shop_code = Integer.parseInt(req.getParameter("shop_code"));
		int itemcode = Integer.parseInt(req.getParameter("item_code"));
		int orders_amount = Integer.parseInt(req.getParameter("orders_amount"));
		int orders_camount = 0;
		
		try {
			String orderCode = "SELECT MAX(ORDERS_CODE) AS MAXCODE FROM ORDERS";
			ps = conn.prepareStatement(orderCode);
			rs = ps.executeQuery();
			rs.next();
			int ordercode = rs.getInt("MAXCODE") + 1;
			
			String shopCode = "SELECT SHOP_CODE AS SHOPCODE FROM SHOP WHERE ACCOUNT_ID=?";
			ps = conn.prepareStatement(shopCode);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int shopcode = rs.getInt("SHOPCODE");
			
			/*String itemCode = "SELECT ITEM_CODE AS ITEMCODE FROM ITEM WHERE ITEM_CODE=?";
			ps = conn.prepareStatement(itemCode);
			ps.setInt(1, item_code);
			rs = ps.executeQuery();
			rs.next();
			int itemcode = rs.getInt("ITEMCODE");*/
			
			String SQL = "INSERT INTO ORDERS(ORDERS_CODE,SHOP_CODE,ITEM_CODE,ORDERS_AMOUNT,ORDERS_CAMOUNT,ORDERS_DATE) "
					+ "VALUES(?, ?, ?, ?, ?, SYSDATE)";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, ordercode);
			ps.setInt(2, shopcode);
			ps.setInt(3, itemcode);
			ps.setInt(4, orders_amount);
			ps.setInt(5, orders_camount);
			ps.executeUpdate();
			
			/*String sitemAmount = "SELECT SITEM_AMOUNT AS SITEMAMOUNT FROM SITEM WHERE SITEM_CODE=?";
			ps = conn.prepareStatement(sitemAmount);
			ps.setInt(1, itemcode);
			rs = ps.executeQuery();
			rs.next();
			int sitemamount = rs.getInt("SITEMAMOUNT");
			int amountresult = sitemamount - orders_amount;
			
			if(amountresult < 0) {
				ordermsg = "주문수량이 재고량보다 많습니다.";
				req.setAttribute("ordermsg", ordermsg);
				
			} else if(orders_amount == 0) {
				ordermsg = "주문수량을 제대로 입력해주세요.";
				req.setAttribute("ordermsg", ordermsg);
			} else {
				String sitemCode = "UPDATE SITEM SET SITEM_AMOUNT=? WHERE SITEM_CODE=?";
				ps = conn.prepareStatement(sitemCode);
				ps.setInt(1, amountresult);
				ps.setInt(2, itemcode);
				ps.executeUpdate();
				System.out.println("실행완료");
				
				ordermsg = "주문이 완료되었습니다.";
				req.setAttribute("ordermsg", ordermsg);
			}*/
			
		} catch(SQLException e) {
			System.out.println("DB 연결실패");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/order/order.jsp");
	}
}
