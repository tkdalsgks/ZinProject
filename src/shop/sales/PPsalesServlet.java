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

@WebServlet("/ppsales")
public class PPsalesServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			String salesCode = "SELECT MAX(SALES_CODE) CODE, MAX(SALES_NUMBER) CODE2 FROM SALES";
			ps = conn.prepareStatement(salesCode);
			rs = ps.executeQuery();
			rs.next();
			int salescode = rs.getInt("CODE") + 1;
			int salesnumber = rs.getInt("CODE") + 1;
			
			String sitemCode = "SELECT SITEM_CODE FROM SITEM";
			ps = conn.prepareStatement(sitemCode);
			rs = ps.executeQuery();
			rs.next();
			int sitemcode = rs.getInt("SITEM_CODE");
			System.out.println(sitemcode);
			
			String salesAmount = "SELECT SUM(PSALES_AMOUNT) AS PSALES_AMOUNT FROM PSALES";
			ps = conn.prepareStatement(salesAmount);
			rs = ps.executeQuery();
			rs.next();
			int salesamount = rs.getInt("PSALES_AMOUNT");
			System.out.println(salesamount);
			
			String salesPrice = "SELECT NVL(SUM(I.ITEM_PRICE * P.PSALES_AMOUNT), 0) AS SALES_SUM "
					+ "FROM ACCOUNT A, ITEM I, PSALES P "
					+ "WHERE A.ACCOUNT_ID=? AND P.SITEM_CODE=I.ITEM_CODE";
			ps = conn.prepareStatement(salesPrice);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int salesprice = rs.getInt("SALES_SUM");
			System.out.println(salesprice);
			
			String psalesCode = "INSERT INTO SALES(SALES_CODE,SALES_NUMBER,SITEM_CODE,PAY_CODE,SALES_AMOUNT,SALES_PRICE,SALES_SORT,SALES_DATE) "
					+ "VALUES(?, ?, ?, 1, ?, ?, 1, SYSDATE)";
			
			ps = conn.prepareStatement(psalesCode);
			ps.setInt(1, salescode);
			ps.setInt(2, salesnumber);
			ps.setInt(3, sitemcode);
			ps.setInt(4, salesamount);
			ps.setInt(5, salesprice);
			ps.executeUpdate();
			
			String deleteCode = "DELETE FROM PSALES WHERE ACCOUNT_ID=?";
			ps = conn.prepareStatement(deleteCode);
			ps.setString(1, account_id);
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
