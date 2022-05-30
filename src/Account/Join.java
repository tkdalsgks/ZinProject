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

@WebServlet("/join")
public class Join extends HttpServlet {
	
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("join.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		
		//String company_SQL = "insert into company values(?, ?)";
		//String account_SQL = "insert into account values(?, ?, ?, 0)";
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int insert = 0;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		String account_id = req.getParameter("account_id");
		//int company_code = Integer.parseInt(req.getParameter("company_code"));
		String account_pwd = req.getParameter("account_pwd");
		
		String type= req.getParameter("type");
		String company_name = req.getParameter("company_name");
		String team_name = req.getParameter("team_name");
		String member_name = req.getParameter("member_name");
		String shop_name = req.getParameter("shop_name");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			if(type.equals("shop")) {  // 점포 주인 회원가입
				String isShop = "select count(*) as cnt from shop where shop_name=?";
				ps1 = conn.prepareStatement(isShop);
				ps1.setString(1, shop_name);
				rs1 = ps1.executeQuery();
				rs1.next();
				if(rs1.getInt("cnt")==0) { // 계약된 가맹점이 아닐경우,
					
					String errormsg = "해당 점포는 계약된 점포가 아닙니다.";
					
					session.setAttribute("errormsg", errormsg);
					
					resp.sendRedirect("join.jsp");
					
				}else {
					String accountInsert = "insert into account(account_id,account_pwd,company_code) values(?,?,1)";
					ps2 = conn.prepareStatement(accountInsert);
					ps2.setString(1, account_id);
					ps2.setString(2, account_pwd);
					ps2.executeUpdate();
					
					String shopUpdate = "update shop set account_id=? where shop_name=?";
					ps2 = conn.prepareStatement(shopUpdate);
					ps2.setString(1, account_id);
					ps2.setString(2, shop_name);
					ps2.executeUpdate();
					
					resp.sendRedirect("login.jsp");
				}
				
				
			}else {  // 본사직원 회원가입
				
				
				String isMember = "select count(*) as cnt from company c, team t "
						+ "where c.company_name=? and c.company_code=t.company_code and t.team_name=?";
				ps1 = conn.prepareStatement(isMember);
				ps1.setString(1, company_name);
				ps1.setString(2, team_name);
				rs1 = ps1.executeQuery();
				rs1.next();
				if(rs1.getInt("cnt")==0) { // 본사 직원이 아닐경우,
					
					String errormsg = "일치하는 본사 정보가 없습니다.";
					
					session.setAttribute("errormsg", errormsg);
					
					resp.sendRedirect("join.jsp");
					
				}else {
					String accountInsert = "insert into account(account_id,account_pwd,company_code) values(?,?,1)";
					ps2 = conn.prepareStatement(accountInsert);
					ps2.setString(1, account_id);
					ps2.setString(2, account_pwd);
					ps2.executeUpdate();
					
					String memberCode = "select max(member_code) as maxcode from member";
					ps2 = conn.prepareStatement(memberCode);
					rs2 = ps2.executeQuery();
					rs2.next();
					int code = rs2.getInt("maxcode") + 1;
					
					String teamCode = "select team_code from team where team_name=?";
					ps2 = conn.prepareStatement(teamCode);
					ps2.setString(1, team_name);
					rs2 = ps2.executeQuery();
					rs2.next();
					int teamcode = rs2.getInt("team_code");
					
					String memberInsert = "insert into member(member_code,team_code,member_name,account_id) values(?,?,?,?)";
					ps2 = conn.prepareStatement(memberInsert);
					ps2.setInt(1, code);
					ps2.setInt(2, teamcode);
					ps2.setString(3, member_name);
					ps2.setString(4, account_id);
					ps2.executeUpdate();
					
					resp.sendRedirect("login.jsp");
				}
				
				
				
			}
		}catch(ClassNotFoundException e) {
			System.out.println("Driver 미설치 또는 드라이버이름 오류");
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		// account
		/*try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			ps2 = conn.prepareStatement(company_SQL);
			ps2.setInt(1, company_code);
			ps2.setString(2, company_name);
			insert = ps2.executeUpdate();
			
			ps = conn.prepareStatement(account_SQL);
			ps.setString(1, account_id);
			ps.setInt(2, company_code);
			ps.setString(3, account_pwd);
			insert = ps.executeUpdate();
			
			
			if(insert > 0) {
				resp.sendRedirect("login.jsp");
			}
			
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
		
		*/
		
		
		
	}
}
