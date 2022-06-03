package Member;

import java.io.IOException;
import java.sql.Connection;
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

/**
 * Servlet implementation class ShopMod
 */
@WebServlet("/shopmod")
public class ShopMod extends HttpServlet {
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		int shop_code = Integer.parseInt(req.getParameter("code"));
		
		Connection conn = null;
		
		String accountId = "select shop_name from shop where shop_code=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
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
		
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("shopmod.jsp");
		requestDispatcher.forward(req, resp);
	}
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String shop_name = req.getParameter("shop_name");
		int shop_code = Integer.parseInt(req.getParameter("shop_code"));
		String account_id = (String)session.getAttribute("account_id");
		System.out.println(shop_code +" : 점포명 변경 > " + shop_name);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			String insertShop = "update shop set shop_name=? where shop_code=?";
			ps = conn.prepareStatement(insertShop);
			ps.setString(1, shop_name);
			ps.setInt(2, shop_code);
			ps.executeUpdate();
			
			
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
		
		String cPath = req.getContextPath();
		//resp.sendRedirect("login.jsp");
		resp.sendRedirect(cPath + "/shopadmin");
	}

}
