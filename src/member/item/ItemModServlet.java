package member.item;

import java.io.IOException;
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

/**
 * Servlet implementation class ShopMod
 */
@WebServlet("/itemmod")
public class ItemModServlet extends MyServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		int item_code = Integer.parseInt(req.getParameter("code"));
		
		String itemName = "select * from item where item_code=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(itemName);
			ps.setInt(1, item_code);
			rs = ps.executeQuery();
			rs.next();
			String item_name = rs.getString("item_name");
			int item_price = rs.getInt("item_price");
			int company_code = rs.getInt("company_code");
			
			req.setAttribute("item_code", item_code);
			req.setAttribute("item_name", item_name);
			req.setAttribute("item_price", item_price);
			req.setAttribute("company_code", company_code);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "itemmod.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String item_name = req.getParameter("item_name");
		int item_code = Integer.parseInt(req.getParameter("item_code"));
		int item_price = Integer.parseInt(req.getParameter("item_price"));

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String insertShop = "update item set item_name=?,item_price=? where item_code=?";
			ps = conn.prepareStatement(insertShop);
			ps.setString(1, item_name);
			ps.setInt(2, item_price);
			ps.setInt(3, item_code);
			ps.executeUpdate();
			
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		String cPath = req.getContextPath();
		resp.sendRedirect(cPath + "/itemadmin");
	}

}
