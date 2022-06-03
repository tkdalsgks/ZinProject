package Shop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/p.orders")
public class Order extends HttpServlet {
	
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("order.jsp");
		requestDispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		int shop_code = Integer.parseInt(req.getParameter("shop_code"));
		int item_code = Integer.parseInt(req.getParameter("item_code"));
		int orders_amount = Integer.parseInt(req.getParameter("orders_amount"));
		int orders_camount = 0;
		//int orders_sort = Integer.parseInt(req.getParameter("orders_sort"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			String orderCode = "select max(orders_code) as maxcode from orders";
			ps2 = conn.prepareStatement(orderCode);
			rs2 = ps2.executeQuery();
			rs2.next();
			int ordercode = rs2.getInt("maxcode") + 1;
			
			String shopCode = "select shop_code as shopcode from shop where shop_code=?";
			ps2 = conn.prepareStatement(shopCode);
			ps2.setInt(1, shop_code);
			rs2 = ps2.executeQuery();
			rs2.next();
			int shopcode = rs2.getInt("shopcode");
			
			String itemCode = "select item_code as itemcode from item where item_code=?";
			ps2 = conn.prepareStatement(itemCode);
			ps2.setInt(1, item_code);
			rs2 = ps2.executeQuery();
			rs2.next();
			int itemcode = rs2.getInt("itemcode");
			
			String SQL = "insert into orders(orders_code,shop_code,item_code,orders_amount,orders_camount,orders_date) "
					+ "values(?, ?, ?, ?, ?, sysdate)";
			ps2 = conn.prepareStatement(SQL);
			ps2.setInt(1, ordercode);
			ps2.setInt(2, shopcode);
			ps2.setInt(3, itemcode);
			ps2.setInt(4, orders_amount);
			ps2.setInt(5, orders_camount);
			//ps2.setInt(6, orders_sort);
			ps2.executeUpdate();
			
			String sitemAmount = "select sitem_amount as sitemamount from sitem where sitem_code=?";
			ps2 = conn.prepareStatement(sitemAmount);
			ps2.setInt(1, itemcode);
			rs2 = ps2.executeQuery();
			rs2.next();
			int sitemamount = rs2.getInt("sitemamount");
			int amountresult = sitemamount - orders_amount;
			System.out.println(amountresult + "로 amount 바꾸기");
			
			String sitemCode = "update sitem set sitem_amount=? where sitem_code=?";
			ps2 = conn.prepareStatement(sitemCode);
			ps2.setInt(1, amountresult);
			ps2.setInt(2, itemcode);
			ps2.executeUpdate();
			System.out.println("실행완료");
			
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
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("order.jsp");
		requestDispatcher.forward(req, resp);
		
	}
}
