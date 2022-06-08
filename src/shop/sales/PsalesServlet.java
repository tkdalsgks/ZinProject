package shop.sales;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.item.itemDTO.ItemDTO;
import util.DBConnection;
import util.MyServlet;

@WebServlet("/psales")
public class PsalesServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		int item_code = Integer.parseInt(req.getParameter("item_code"));
		int item_amount = Integer.parseInt(req.getParameter("item_amount"));
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			String psalesCode = "SELECT MAX(PSALES_CODE) AS MAXCODE FROM PSALES";
			ps = conn.prepareStatement(psalesCode);
			rs = ps.executeQuery();
			rs.next();
			int psalescode = rs.getInt("MAXCODE") + 1;
			
			String psales = "INSERT INTO PSALES(PSALES_CODE,ITEM_CODE,PSALES_AMOUNT,ACCOUNT_ID) "
					+ "VALUES(?, ?, ?, ?)";
			ps = conn.prepareStatement(psales);
			ps.setInt(1, psalescode);
			ps.setInt(2, item_code);
			ps.setInt(3, item_amount);
			ps.setString(4, account_id);
			ps.executeUpdate();
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		resp.sendRedirect("i.sales");
	}

}
