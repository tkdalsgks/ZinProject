package Shop;

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

@WebServlet("/item")
public class Item extends HttpServlet {
	
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("item.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = null;
		
		String SQL = "insert into item values(?, ?, ?, ?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		int insert = 0;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		int item_code = Integer.parseInt(req.getParameter("item_code"));
		int company_code = Integer.parseInt(req.getParameter("company_code"));
		String item_name = req.getParameter("item_name");
		int item_price = Integer.parseInt(req.getParameter("item_price"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, item_code);
			ps.setInt(2, company_code);
			ps.setString(3, item_name);
			ps.setInt(4, item_price);
			insert = ps.executeUpdate();
			
			if(insert > 0) {
				session.setAttribute("company_code", company_code);
				resp.sendRedirect("item.jsp");
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
