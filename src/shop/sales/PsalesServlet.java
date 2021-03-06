package shop.sales;

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

@WebServlet("/psales")
public class PsalesServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		int sitem_code = Integer.parseInt(req.getParameter("sitem_code"));
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
			
			String duplicate = "SELECT COUNT(*) AS CNT FROM PSALES WHERE ACCOUNT_ID=? AND SITEM_CODE=?";
			ps = conn.prepareStatement(duplicate);
			ps.setString(1, account_id);
			ps.setInt(2, sitem_code);
			rs = ps.executeQuery();
			rs.next();
			int cnt = rs.getInt("CNT");
			
			if(cnt >= 1) {
				String update = "UPDATE PSALES SET SALES_AMOUNT=SALES_AMOUNT+? WHERE ACCOUNT_ID=? AND SITEM_CODE=?";
				ps = conn.prepareStatement(update);
				ps.setInt(1, item_amount);
				ps.setString(2, account_id);
				ps.setInt(3, sitem_code);
				ps.executeUpdate();
			} else {
				String psales = "INSERT INTO PSALES(PSALES_CODE,SITEM_CODE,SALES_AMOUNT,SALES_PRICE,ACCOUNT_ID) "
						+ "VALUES(?, ?, ?, ?, ?)";
				ps = conn.prepareStatement(psales);
				ps.setInt(1, psalescode);
				ps.setInt(2, sitem_code);
				ps.setInt(3, item_amount);
				ps.setInt(4, 1);
				ps.setString(5, account_id);
				ps.executeUpdate();
			}
			
			String deleteItem = "UPDATE SITEM SET SITEM_AMOUNT=SITEM_AMOUNT-? WHERE SITEM_CODE=?";
			ps = conn.prepareStatement(deleteItem);
			ps.setInt(1, item_amount);
			ps.setInt(2, sitem_code);
			ps.executeUpdate();
			
		} catch(SQLException e) {
			System.out.println("DB ?????? ???????????? SQL ?????? ??????");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		resp.sendRedirect("i.sales");
	}

}
