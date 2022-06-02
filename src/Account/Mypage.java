package Account;

import java.io.IOException;
import java.io.PrintWriter;
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

import dto.AccountDTO;

@WebServlet("/mypage")
public class Mypage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		PrintWriter out = resp.getWriter();
		
		Connection conn = null;
		
		String SQL = "select * from account where account_id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			AccountDTO accountdto = new AccountDTO();
			if(rs.next()) {
				accountdto.setAccount_id(account_id);
				accountdto.setAccount_pwd(rs.getString("account_pwd"));
			}
			
			String isShop = "select count(*) as cnt from shop where account_id=?";
			ps = conn.prepareStatement(isShop);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int result = rs.getInt("cnt");
			
			if(result ==0) { // 본사직원이라면
				String membername = "select member_name from member where account_id=?";
				ps = conn.prepareStatement(membername);
				ps.setString(1, account_id);
				rs = ps.executeQuery();
				if(rs.next()) {
					String name = rs.getString("member_name");
					accountdto.setMember_name(name);
					accountdto.setShop_name(null);
				}
			}else {	// 점포주인이라면
				String shopname = "select shop_name from shop where account_id=?";
				ps = conn.prepareStatement(shopname);
				ps.setString(1, account_id);
				rs = ps.executeQuery();
				if(rs.next()) {
					String name = rs.getString("shop_name");
					accountdto.setShop_name(name);
					accountdto.setMember_name(null);
				}
			}
			
			req.setAttribute("myinfo", accountdto);
			rs.close();
			ps.close();
			
			
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
		
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("mypage.jsp");
		requestDispatcher.forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
