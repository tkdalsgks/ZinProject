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
		
		String company_SQL = "insert into company values(?, ?)";
		String account_SQL = "insert into account values(?, ?, ?, 0)";
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		int insert = 0;
		req.setCharacterEncoding("UTF-8");
		
		String account_id = req.getParameter("account_id");
		int company_code = Integer.parseInt(req.getParameter("company_code"));
		String account_pwd = req.getParameter("account_pwd");
		
		String company_name = req.getParameter("company_name");
		
		// account
		try {
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
		
		
	}
}
