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

import util.DBConnection;
import util.MyServlet;

@WebServlet("/shopdelete")
public class ShopDeleteServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		int shop_code = Integer.parseInt(req.getParameter("code"));
		
		String accountId = "SELECT ACCOUNT_ID FROM SHOP WHERE SHOP_CODE=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(accountId);
			ps.setInt(1, shop_code);
			rs = ps.executeQuery();
			rs.next();
			String account_id = rs.getString("account_id");
			
			String deleteShop = "DELETE FROM SHOP WHERE SHOP_CODE=?";
			
			ps = conn.prepareStatement(deleteShop);
			ps.setInt(1, shop_code);
			ps.executeUpdate();
			
			String deleteAccount = "DELETE FROM ACCOUNT WHERE ACCOUNT_ID=?";
			
			ps = conn.prepareStatement(deleteAccount);
			ps.setString(1, account_id);
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
