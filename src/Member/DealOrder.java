package Member;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.OrdersDTO;
import dto.ShopDTO;

@WebServlet("/dealorder")
public class DealOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
      
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int orders_code = Integer.parseInt(req.getParameter("code"));
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			String ordersinfo = "select * from orders where orders_code=?";
			ps = conn.prepareStatement(ordersinfo);
			ps.setInt(1, orders_code);
			rs = ps.executeQuery();
			rs.next();
			OrdersDTO dto = new OrdersDTO();
			dto.setOrders_code(orders_code);
			dto.setShop_code(rs.getInt("shop_code"));
			dto.setItem_code(rs.getInt("item_code"));
			dto.setOrders_amount(rs.getInt("orders_amount"));
			dto.setOrders_date(rs.getDate("orders_date"));
			
			req.setAttribute("ordersinfo", dto);
			
			
			
		} catch(ClassNotFoundException e) {
			System.out.println("Driver 미설치 또는 드라이버이름 오류");
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("dealorder.jsp");
		requestDispatcher.forward(req, resp);
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		
		int orders_code = Integer.parseInt(req.getParameter("orders_code"));
		int orders_camount = Integer.parseInt(req.getParameter("orders_camount"));
		int shop_code = Integer.parseInt(req.getParameter("shop_code"));
		int item_code = Integer.parseInt(req.getParameter("item_code"));
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			String dealOrder = "update orders set orders_camount=?,orders_sort=1 where orders_code=?";
			ps = conn.prepareStatement(dealOrder);
			ps.setInt(1, orders_camount);
			ps.setInt(2, orders_code);
			ps.executeUpdate();
			//System.out.println("확정 주문 update");
			
			String SitemAmount = "select * from sitem where shop_code=? and item_code=?";
			ps = conn.prepareStatement(SitemAmount);
			ps.setInt(1, shop_code);
			ps.setInt(2, item_code);
			rs = ps.executeQuery();
			//System.out.println("이 상품을 주문했는지 안했는지");
			
			if(rs.next()) {  // 해당 매장에 주문상품이 이미 있었다면
				//System.out.println("주문한 적 있는 상품");
				int sitem_code = rs.getInt("sitem_code");
				int sitem_amount = rs.getInt("sitem_amount") + orders_camount;
				
				String updateSitem = "update sitem set sitem_amount=? where sitem_code=?";
				ps = conn.prepareStatement(updateSitem);
				ps.setInt(1, sitem_amount);
				ps.setInt(2, sitem_code);
				ps.executeUpdate();
				//System.out.println("--------");
			}else {  // 이 상품을 처음 주문하는거라면
				//System.out.println("상품 첫주문");
				String SitemCode = "select max(sitem_code) as sitem_code from sitem";
				ps = conn.prepareStatement(SitemCode);
				rs = ps.executeQuery();
				rs.next();
				int sitem_code = rs.getInt("sitem_code")+1;
				int sitem_amount = orders_camount;
				
				String insertSitem = "insert into sitem(sitem_code,shop_code,item_code,sitem_amount) values (?,?,?,?)";
				ps = conn.prepareStatement(insertSitem);
				ps.setInt(1, sitem_code);
				ps.setInt(2, shop_code);
				ps.setInt(3, item_code);
				ps.setInt(4, sitem_amount);
				ps.executeUpdate();
				//System.out.println("--------");
			}
			
			
		} catch(ClassNotFoundException e) {
			System.out.println("Driver 미설치 또는 드라이버이름 오류");
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		//System.out.println("insert완료");
		
		String cPath = req.getContextPath();
		//resp.sendRedirect("login.jsp");
		resp.sendRedirect(cPath + "/d.orders");
		
		
	}

}