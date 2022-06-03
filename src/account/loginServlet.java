package account;

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

@WebServlet("/login")
public class loginServlet extends MyServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.forward(req, resp, "/WEB-INF/views/account/login.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		String SQL = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID=? AND ACCOUNT_PWD=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		String account_id = req.getParameter("account_id");
		String account_pwd = req.getParameter("account_pwd");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			ps.setString(2, account_pwd);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("account_pwd").equals(account_pwd)) {
					account_id = rs.getString("account_id");
					session.setAttribute("account_id", account_id);
					
					
					// 로그인 시도 횟수 증가
					
					String logintrysql = "SELECT ACCOUNT_COUNT FROM ACCOUNT WHERE ACCOUNT_ID=?";
					PreparedStatement pt5 = conn.prepareStatement(logintrysql);
					pt5.setString(1,account_id);
					ResultSet rs5 = pt5.executeQuery();
					int result=0;
					if(rs5.next()) {
						result = rs.getInt("account_count");
					}
					
					logintrysql = "UPDATE ACCOUNT SET ACCOUNT_COUNT=? WHERE ACCOUNT_ID=?";
					pt5 = conn.prepareStatement(logintrysql);
					pt5.setInt(1, result+1);
					pt5.setString(2, account_id);
					pt5.executeUpdate();
					
					
					
					String company_name = null;
					String team_name = null;
					String member_name = null;
					String shop_name = null;
					
					
					String typeSQL = "SELECT COUNT(*) AS CNT FROM SHOP S, ACCOUNT A "
							+ "WHERE A.ACCOUNT_ID=? AND S.ACCOUNT_ID=A.ACCOUNT_ID";
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					ps1 = conn.prepareStatement(typeSQL);
					ps1.setString(1, account_id);
					rs1 = ps1.executeQuery();
					rs1.next();
					int tmp = rs1.getInt("CNT");
					
					if(tmp == 0) { // 팀원인 경우
						PreparedStatement ps2 = null;
						ResultSet rs2 = null;
						String memberSQL = "SELECT C.COMPANY_NAME,T.TEAM_NAME,M.MEMBER_NAME "
								+ "FROM COMPANY C, TEAM T, MEMBER M, ACCOUNT A "
								+ "WHERE A.ACCOUNT_ID=? "
								+ "AND A.ACCOUNT_ID=M.ACCOUNT_ID AND M.TEAM_CODE=T.TEAM_CODE AND T.COMPANY_CODE=C.COMPANY_CODE";
						ps2 = conn.prepareStatement(memberSQL);
						ps2.setString(1, account_id);
						rs2 = ps2.executeQuery();
						if(rs2.next()) {
							company_name = rs2.getString("company_name");
							team_name = rs2.getString("team_name");
							member_name = rs2.getString("member_name");
						}
						
					}else {  // 점포인 경우
						PreparedStatement ps2 = null;
						ResultSet rs2 = null;
						String shopSQL = "SELECT S.SHOP_NAME, M.MEMBER_NAME "
								+ "FROM SHOP S, MEMBER M, ACCOUNT A "
								+ "WHERE A.ACCOUNT_ID=? AND A.ACCOUNT_ID=S.ACCOUNT_ID AND M.MEMBER_CODE=S.MEMBER_CODE";
						ps2 = conn.prepareStatement(shopSQL);
						ps2.setString(1, account_id);
						rs2 = ps2.executeQuery();
						if(rs2.next()) {
							shop_name = rs2.getString("shop_name");
							member_name = rs2.getString("member_name");
						}
					}
					session.setAttribute("company_name", company_name);
					session.setAttribute("team_name", team_name);
					session.setAttribute("member_name", member_name);
					session.setAttribute("shop_name", shop_name);
					
					String cPath = req.getContextPath();
					resp.sendRedirect(cPath);
				} else {
					super.forward(req, resp, "/WEB-INF/views/error.jsp");
				}
			}else {
				super.forward(req, resp, "/WEB-INF/views/error.jsp");
			}
			
		} catch(SQLException e) {
			System.out.println("DB 연결실패");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
	}
}
