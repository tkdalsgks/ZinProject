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

@WebServlet("/shopinsert")
public class ShopInsertServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		String SQL = "SELECT MEMBER_NAME FROM MEMBER WHERE ACCOUNT_ID=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			String member_name = rs.getString("member_name");
			
			req.setAttribute("member_name", member_name);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/shop/shopinsert.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String shop_name = req.getParameter("shop_name");
		String account_id = (String)session.getAttribute("account_id");
		
		String SQL = "SELECT MEMBER_CODE FROM MEMBER WHERE ACCOUNT_ID=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int member_code = rs.getInt("member_code");
			//System.out.println("membercode 가져옴");
			
			String companyCode = "SELECT P.COMPANY_CODE FROM COMPANY P, TEAM T, MEMBER M "
					+ "WHERE M.MEMBER_CODE=? AND M.TEAM_CODE=T.TEAM_CODE AND T.COMPANY_CODE=P.COMPANY_CODE";
			ps = conn.prepareStatement(companyCode);
			ps.setInt(1, member_code);
			rs = ps.executeQuery();
			rs.next();
			int company_code = rs.getInt("company_code");
			//System.out.println("companycode 가져옴");
			
			String shopCode = "SELECT MAX(SHOP_CODE) AS SHOP_CODE FROM SHOP";
			ps = conn.prepareStatement(shopCode);
			rs = ps.executeQuery();
			rs.next();
			int shop_code = rs.getInt("shop_code") + 1;
			//System.out.println("shopcode 가져옴");
			
			String insertShop = "INSERT INTO SHOP(SHOP_CODE,COMPANY_CODE,MEMBER_CODE,SHOP_NAME) VALUES(?,?,?,?)";
			ps = conn.prepareStatement(insertShop);
			ps.setInt(1, shop_code);
			ps.setInt(2, company_code);
			ps.setInt(3, member_code);
			ps.setString(4, shop_name);
			ps.executeUpdate();
			//System.out.println("insert 완료");
			
			
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
