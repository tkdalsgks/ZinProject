package Member;

import java.io.IOException;
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

@WebServlet("/shopdelete")
public class ShopDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int shop_code = Integer.parseInt(req.getParameter("code"));
		
		Connection conn = null;
		
		String accountId = "select account_id from shop where shop_code=?";
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
			String account_id = rs.getString("account_id");
			
			String deleteShop = "delete from shop where shop_code=?";
			
			ps = conn.prepareStatement(deleteShop);
			ps.setInt(1, shop_code);
			ps.executeUpdate();
			
			String deleteAccount = "delete from account where account_id=?";
			
			ps = conn.prepareStatement(deleteAccount);
			ps.setString(1, account_id);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
