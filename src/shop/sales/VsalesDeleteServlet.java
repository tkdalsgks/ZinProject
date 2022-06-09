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

import util.DBConnection;
import util.MyServlet;

@WebServlet("/vsalesdelete")
public class VsalesDeleteServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		int sales_code = Integer.parseInt(req.getParameter("salescode"));
		int sales_number = Integer.parseInt(req.getParameter("salesnumber"));
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			String salesInfo = "SELECT * FROM SALES WHERE SALES_CODE=? AND SALES_NUMBER=?";
			ps = conn.prepareStatement(salesInfo);
			ps.setInt(1, sales_code);
			ps.setInt(2, sales_number);
			rs = ps.executeQuery();
			if(rs.next()) {
				int sitem_code = rs.getInt("SITEM_CODE");
				int sales_amount = rs.getInt("SALES_AMOUNT");
				String updateItem = "UPDATE SITEM SET SITEM_AMOUNT=SITEM_AMOUNT+? WHERE SITEM_CODE=?";
				ps = conn.prepareStatement(updateItem);
				ps.setInt(1, sales_amount);
				ps.setInt(2, sitem_code);
				ps.executeUpdate();
			}
			
			String deleteSales = "UPDATE SALES SET SALES_SORT=0 WHERE SALES_CODE=? AND SALES_NUMBER=?";
			
			ps = conn.prepareStatement(deleteSales);
			ps.setInt(1, sales_code);
			ps.setInt(2, sales_number);
			ps.executeUpdate();
			
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		String cPath = req.getContextPath();
		resp.sendRedirect(cPath + "/v.sales");
	}

}
