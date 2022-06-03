package account;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import account.accountDTO.AccountDTO;
import util.DBConnection;
import util.MyServlet;

@WebServlet("/mypage")
public class MypageServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		PrintWriter out = resp.getWriter();
		
		String SQL = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			AccountDTO accountdto = new AccountDTO();
			if(rs.next()) {
				accountdto.setAccount_id(account_id);
				accountdto.setAccount_pwd(rs.getString("account_pwd"));
			}
			
			String isShop = "SELECT COUNT(*) AS CNT FROM SHOP WHERE ACCOUNT_ID=?";
			ps = conn.prepareStatement(isShop);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int result = rs.getInt("CNT");
			
			if(result ==0) { // 본사직원이라면
				String membername = "SELECT MEMBER_NAME FROM MEMBER WHERE ACCOUNT_ID=?";
				ps = conn.prepareStatement(membername);
				ps.setString(1, account_id);
				rs = ps.executeQuery();
				if(rs.next()) {
					String name = rs.getString("member_name");
					accountdto.setMember_name(name);
					accountdto.setShop_name(null);
				}
			}else {	// 점포주인이라면
				String shopname = "SELECT SHOP_NAME FROM SHOP WHERE ACCOUNT_ID=?";
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
			
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/account/mypage.jsp");
	}

}
