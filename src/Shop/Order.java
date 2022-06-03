package Shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.OrdersDTO;

@WebServlet("/p.orders")
public class Order extends HttpServlet {
	
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("ordermsg");
		resp.sendRedirect("order.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String ordermsg = null;
		
		Connection conn = null;
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		int shop_code = Integer.parseInt(req.getParameter("shop_code"));
		int item_code = Integer.parseInt(req.getParameter("item_code"));
		int orders_amount = Integer.parseInt(req.getParameter("orders_amount"));
		int orders_camount = orders_amount;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			String orderCode = "select max(orders_code) as maxcode from orders";
			ps = conn.prepareStatement(orderCode);
			rs = ps.executeQuery();
			rs.next();
			int ordercode = rs.getInt("maxcode") + 1;
			
			String shopCode = "select shop_code as shopcode from shop where shop_code=?";
			ps = conn.prepareStatement(shopCode);
			ps.setInt(1, shop_code);
			rs = ps.executeQuery();
			rs.next();
			int shopcode = rs.getInt("shopcode");
			
			String itemCode = "select item_code as itemcode from item where item_code=?";
			ps = conn.prepareStatement(itemCode);
			ps.setInt(1, item_code);
			rs = ps.executeQuery();
			rs.next();
			int itemcode = rs.getInt("itemcode");
			
			String SQL = "insert into orders(orders_code,shop_code,item_code,orders_amount,orders_camount,orders_date) "
					+ "values(?, ?, ?, ?, ?, sysdate)";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, ordercode);
			ps.setInt(2, shopcode);
			ps.setInt(3, itemcode);
			ps.setInt(4, orders_amount);
			ps.setInt(5, orders_camount);
			ps.executeUpdate();
			
			String sitemAmount = "select sitem_amount as sitemamount from sitem where sitem_code=?";
			ps = conn.prepareStatement(sitemAmount);
			ps.setInt(1, itemcode);
			rs = ps.executeQuery();
			rs.next();
			int sitemamount = rs.getInt("sitemamount");
			int amountresult = sitemamount - orders_amount;
			System.out.println(amountresult + "로 amount 바꾸기");
			
			if(amountresult < 0) {
				ordermsg = "주문수량이 재고량보다 많습니다.";
				session.setAttribute("ordermsg", ordermsg);
				
			} else if(orders_amount == 0) {
				ordermsg = "주문수량을 제대로 입력해주세요.";
				session.setAttribute("ordermsg", ordermsg);
			} else {
				String sitemCode = "update sitem set sitem_amount=? where sitem_code=?";
				ps = conn.prepareStatement(sitemCode);
				ps.setInt(1, amountresult);
				ps.setInt(2, itemcode);
				ps.executeUpdate();
				System.out.println("실행완료");
				
				ordermsg = "주문이 완료되었습니다.";
				session.setAttribute("ordermsg", ordermsg);
			}
			
		} catch(ClassNotFoundException e) {
			System.out.println("Driver 문제");
		} catch(SQLException e) {
			System.out.println("DB 연결실패");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		resp.sendRedirect("order.jsp");
		
	}
}
