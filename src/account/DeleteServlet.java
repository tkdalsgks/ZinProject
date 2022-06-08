package account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DBConnection;
import util.MyServlet;

@WebServlet("/delete")
public class DeleteServlet extends MyServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.forward(req, resp, "/WEB-INF/views/account/delete.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		String SQL = "DELETE FROM ACCOUNT WHERE ACCOUNT_ID=?";
		PreparedStatement ps = null;
		int delete = 0;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		String account_id = req.getParameter("account_id");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			delete = ps.executeUpdate();
			
			session.invalidate();
			super.forward(req, resp, "/WEB-INF/index.jsp");
			
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
