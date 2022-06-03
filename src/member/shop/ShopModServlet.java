package member.shop;

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
@WebServlet("/shopmod")
public class ShopModServlet extends MyServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		int shop_code = Integer.parseInt(req.getParameter("code"));
		
		String accountId = "select shop_name from shop where shop_code=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(accountId);
			ps.setInt(1, shop_code);
			rs = ps.executeQuery();
			rs.next();
			String shop_name = rs.getString("shop_name");
			
			String memberName = "select member_name from member where account_id=?";
			ps = conn.prepareStatement(memberName);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			String member_name = rs.getString("member_name");
			
			req.setAttribute("member_name", member_name);
			req.setAttribute("shop_code", shop_code);
			req.setAttribute("shop_name", shop_name);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "shopmod.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String shop_name = req.getParameter("shop_name");
		int shop_code = Integer.parseInt(req.getParameter("shop_code"));
		String account_id = (String)session.getAttribute("account_id");
		System.out.println(shop_code +" : 점포명 변경 > " + shop_name);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			String insertShop = "update shop set shop_name=? where shop_code=?";
			ps = conn.prepareStatement(insertShop);
			ps.setString(1, shop_name);
			ps.setInt(2, shop_code);
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
		resp.sendRedirect(cPath + "/shopadmin");
	}

}
