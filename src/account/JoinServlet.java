package account;

import java.io.IOException;
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

import util.DBConnection;
import util.MyServlet;

@WebServlet("/join")
public class JoinServlet extends MyServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("errormsg");
		super.forward(req, resp, "/WEB-INF/views/account/join.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int insert = 0;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		String account_id = req.getParameter("account_id");
		String account_pwd = req.getParameter("account_pwd");
		
		String type= req.getParameter("type");
		String company_code = req.getParameter("company_code");
		String team_code = req.getParameter("team_code");
		String member_name = req.getParameter("member_name");
		String shop_code = req.getParameter("shop_code");
		try {
			if(type.equals("shop")) {  // 점포 주인 회원가입
				String isShop = "select count(*) as cnt from shop where shop_code=?";
				ps1 = conn.prepareStatement(isShop);
				ps1.setInt(1, Integer.parseInt(shop_code));
				rs1 = ps1.executeQuery();
				rs1.next();
				if(rs1.getInt("cnt")==0) { // 계약된 가맹점이 아닐경우,
					
					String errormsg = "해당 점포는 계약된 점포가 아닙니다.";
					System.out.println(errormsg);
					session.setAttribute("errormsg", errormsg);
					
					super.forward(req, resp, "/WEB-INF/views/account/join.jsp");
					
				}else {
					String companyCode = "select company_code from shop where shop_code=?";
					ps2 = conn.prepareStatement(companyCode);
					ps2.setInt(1, Integer.parseInt(shop_code));
					rs2 = ps2.executeQuery();
					rs2.next();
					int company_c = rs2.getInt("company_code");
					
					String accountInsert = "insert into account(account_id,account_pwd,company_code) values(?,?,?)";
					ps2 = conn.prepareStatement(accountInsert);
					ps2.setString(1, account_id);
					ps2.setString(2, account_pwd);
					ps2.setInt(3, company_c);
					ps2.executeUpdate();
					
					String shopUpdate = "update shop set account_id=? where shop_code=?";
					ps2 = conn.prepareStatement(shopUpdate);
					ps2.setString(1, account_id);
					ps2.setInt(2, Integer.parseInt(shop_code));
					ps2.executeUpdate();
					
					String cPath = req.getContextPath();
					resp.sendRedirect(cPath + "/login");
				}
				
				
			}else {  // 본사직원 회원가입
				
				
				String isMember = "select count(*) as cnt from company c, team t "
						+ "where c.company_code=? and c.company_code=t.company_code and t.team_code=?";
				ps1 = conn.prepareStatement(isMember);
				ps1.setInt(1, Integer.parseInt(company_code));
				ps1.setInt(2, Integer.parseInt(team_code));
				rs1 = ps1.executeQuery();
				rs1.next();
				if(rs1.getInt("cnt")==0) { // 본사 직원이 아닐경우,
					
					String errormsg = "일치하는 본사 정보가 없습니다.";
					
					session.setAttribute("errormsg", errormsg);
					
					super.forward(req, resp, "/WEB-INF/views/account/join.jsp");
					
				}else {
					String accountInsert = "insert into account(account_id,account_pwd,company_code) values(?,?,?)";
					ps2 = conn.prepareStatement(accountInsert);
					ps2.setString(1, account_id);
					ps2.setString(2, account_pwd);
					ps2.setInt(3, Integer.parseInt(company_code));
					ps2.executeUpdate();
					
					String memberCode = "select max(member_code) as maxcode from member";
					ps2 = conn.prepareStatement(memberCode);
					rs2 = ps2.executeQuery();
					rs2.next();
					int code = rs2.getInt("maxcode") + 1;
					
					/*String teamCode = "select team_code from team where team_name=?";
					ps2 = conn.prepareStatement(teamCode);
					ps2.setString(1, team_name);
					rs2 = ps2.executeQuery();
					rs2.next();
					int teamcode = rs2.getInt("team_code");*/
					
					String memberInsert = "insert into member(member_code,team_code,member_name,account_id) values(?,?,?,?)";
					ps2 = conn.prepareStatement(memberInsert);
					ps2.setInt(1, code);
					ps2.setInt(2, Integer.parseInt(team_code));
					ps2.setString(3, member_name);
					ps2.setString(4, account_id);
					ps2.executeUpdate();
					
					String cPath = req.getContextPath();
					resp.sendRedirect(cPath + "/login");
				}
				
			}
		}catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
	}
}
