package Member;

import java.io.IOException;
import java.io.PrintWriter;
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

import dto.ItemDTO;
import dto.OrdersDTO;
import dto.ShopDTO;


@WebServlet("/nd.orders")
public class Ndorders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		Connection conn = null;
		
		String SQL = "select company_code from account where account_id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int company_code = rs.getInt("company_code");
			
			SQL = "select member_code from member where account_id=?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int member_code = rs.getInt("member_code");
			
			
			String shoplist = "select * from shop where company_code=? and member_code=?";
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
				String orderslist = "select * from orders where shop_code=? and orders_sort=0 order by orders_code desc";
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
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("ndorders.jsp");
		requestDispatcher.forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
