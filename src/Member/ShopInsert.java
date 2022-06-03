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

@WebServlet("/shopinsert")
public class ShopInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		Connection conn = null;
		
		String SQL = "select member_name from member where account_id=?";
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
			String member_name = rs.getString("member_name");
			
			req.setAttribute("member_name", member_name);
			
			
			
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
		
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("shopinsert.jsp");
		requestDispatcher.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String shop_name = req.getParameter("shop_name");
		String account_id = (String)session.getAttribute("account_id");
		
		Connection conn = null;
		
		String SQL = "select member_code from member where account_id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int member_code = rs.getInt("member_code");
			//System.out.println("membercode 가져옴");
			
			String companyCode = "select p.company_code from company p, team t, member m where m.member_code=? and m.team_code = t.team_code and t.company_code=p.company_code";
			ps = conn.prepareStatement(companyCode);
			ps.setInt(1, member_code);
			rs = ps.executeQuery();
			rs.next();
			int company_code = rs.getInt("company_code");
			//System.out.println("companycode 가져옴");
			
			String shopCode = "select max(shop_code) as shop_code from shop";
			ps = conn.prepareStatement(shopCode);
			rs = ps.executeQuery();
			rs.next();
			int shop_code = rs.getInt("shop_code") + 1;
			//System.out.println("shopcode 가져옴");
			
			String insertShop = "insert into shop(shop_code,company_code,member_code,shop_name) values(?,?,?,?)";
			ps = conn.prepareStatement(insertShop);
			ps.setInt(1, shop_code);
			ps.setInt(2, company_code);
			ps.setInt(3, member_code);
			ps.setString(4, shop_name);
			ps.executeUpdate();
			//System.out.println("insert 완료");
			
			
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
