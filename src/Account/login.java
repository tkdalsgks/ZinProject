package Account;

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

@WebServlet("/login")
public class login extends HttpServlet {
	
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		
		String SQL = "select * from account where account_id=? and account_pwd=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		String account_id = req.getParameter("account_id");
		String account_pwd = req.getParameter("account_pwd");
		
		//String company_name = req.getParameter("company_name");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			ps.setString(2, account_pwd);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("account_pwd").equals(account_pwd)) {
					account_id = rs.getString("account_id");
					session.setAttribute("account_id", account_id);
					
					
					// 로그인 시도 횟수 증가
					
					String logintrysql = "select account_count from account where account_id=?";
					PreparedStatement pt5 = conn.prepareStatement(logintrysql);
					pt5.setString(1,account_id);
					ResultSet rs5 = pt5.executeQuery();
					int result=0;
					if(rs5.next()) {
						result = rs.getInt("account_count");
					}
					
					logintrysql = "update account set account_count=? where account_id=?";
					pt5 = conn.prepareStatement(logintrysql);
					pt5.setInt(1, result+1);
					pt5.setString(2, account_id);
					pt5.executeUpdate();
					
					
					
					String company_name = null;
					String team_name = null;
					String member_name = null;
					String shop_name = null;
					
					
					String typeSQL = "select count(*) as cnt from shop s, account a where a.account_id=? and s.account_id=a.account_id";
					PreparedStatement ps1 = null;
					ResultSet rs1 = null;
					ps1 = conn.prepareStatement(typeSQL);
					ps1.setString(1, account_id);
					rs1 = ps1.executeQuery();
					rs1.next();
					int tmp = rs1.getInt("cnt");
					
					if(tmp == 0) { // 팀원인 경우
						PreparedStatement ps2 = null;
						ResultSet rs2 = null;
						String memberSQL = "select c.company_name,t.team_name,m.member_name from company c, team t, member m, account a "+
								"where a.account_id=? and a.account_id=m.account_id and m.team_code=t.team_code and t.company_code=c.company_code";
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
						String shopSQL = "select s.shop_name, m.member_name from shop s, member m, account a "+
								"where a.account_id=? and a.account_id=s.account_id and m.member_code=s.member_code";
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
					
					resp.sendRedirect("index.jsp");
				} else {
					resp.sendRedirect("error.jsp");
				}
			}else {
				resp.sendRedirect("error.jsp");
			}
			
		} catch(ClassNotFoundException e) {
			System.out.println("Driver 문제");
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
