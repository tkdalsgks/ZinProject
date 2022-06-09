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
		int pay_code = Integer.parseInt(req.getParameter("pay_code"));
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			String salesCode = "SELECT MAX(SALES_CODE) CODE0 FROM SALES";
			ps = conn.prepareStatement(salesCode);
			rs = ps.executeQuery();
			rs.next();
			int salescode = rs.getInt("CODE0") + 1;
			int salesnumber = 1;
			
			String ppsalesList = "SELECT * FROM PSALES WHERE ACCOUNT_ID=?";
			ps = conn.prepareStatement(ppsalesList);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			while(rs.next()) {
				int sitem_code = rs.getInt("SITEM_CODE");
				int sales_amount = rs.getInt("SALES_AMOUNT");
				
				String priceInfo = "SELECT ITEM_PRICE FROM ITEM I,SITEM S WHERE I.ITEM_CODE=S.ITEM_CODE AND S.SITEM_CODE=?";
				ps1 = conn.prepareStatement(priceInfo);
				ps1.setInt(1, sitem_code);
				rs1 = ps1.executeQuery();
				rs1.next();
				int item_price = rs1.getInt("ITEM_PRICE");
				
				String psalesCode = "INSERT INTO SALES(SALES_CODE,SALES_NUMBER,SITEM_CODE,PAY_CODE,SALES_AMOUNT,SALES_PRICE,SALES_SORT,SALES_DATE) "
						+ "VALUES(?, ?, ?, ?, ?, ?, 1, SYSDATE)";
				
				ps1 = conn.prepareStatement(psalesCode);
				ps1.setInt(1, salescode);
				ps1.setInt(2, salesnumber++);
				ps1.setInt(3, sitem_code);
				ps1.setInt(4, pay_code);
				ps1.setInt(5, sales_amount);
				ps1.setInt(6, sales_amount * item_price);
				ps1.executeUpdate();
			}
			
			
			/*String sitemCode = "SELECT SITEM_CODE FROM SITEM";
			ps = conn.prepareStatement(sitemCode);
			rs = ps.executeQuery();
			rs.next();
			int sitemcode = rs.getInt("SITEM_CODE");
			
			String salesAmount = "SELECT SUM(SALES_AMOUNT) AS SALES_AMOUNT FROM PSALES WHERE ACCOUNT_ID=?";
			ps = conn.prepareStatement(salesAmount);
			ps.setNString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int salesamount = rs.getInt("SALES_AMOUNT");
			
			String salesPrice = "SELECT NVL(SUM(I.ITEM_PRICE * P.SALES_AMOUNT), 0) AS SALES_SUM "
					+ "FROM ITEM I, PSALES P, SITEM S "
					+ "WHERE P.ACCOUNT_ID=? AND P.SITEM_CODE=S.SITEM_CODE AND S.ITEM_CODE=I.ITEM_CODE";
			ps = conn.prepareStatement(salesPrice);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int salesprice = rs.getInt("SALES_SUM");
			
			String psalesCode = "INSERT INTO SALES(SALES_CODE,SALES_NUMBER,SITEM_CODE,PAY_CODE,SALES_AMOUNT,SALES_PRICE,SALES_SORT,SALES_DATE) "
					+ "VALUES(?, ?, ?, 1, ?, ?, 1, SYSDATE)";
			
			ps = conn.prepareStatement(psalesCode);
			ps.setInt(1, salescode);
			ps.setInt(2, salesnumber);
			ps.setInt(3, sitemcode);
			ps.setInt(4, salesamount);
			ps.setInt(5, salesprice);
			ps.executeUpdate();*/
			
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
