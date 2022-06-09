package shop.sales;

import java.io.IOException;
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

@WebServlet("/i.sales")
public class IsalesServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		String SQL = "SELECT S.SHOP_CODE FROM ACCOUNT A, SHOP S WHERE A.ACCOUNT_ID=? AND A.ACCOUNT_ID=S.ACCOUNT_ID";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int shop_code = rs.getInt("shop_code");
			
			String itemlist = "SELECT S.SITEM_CODE,I.ITEM_CODE,I.ITEM_NAME,I.ITEM_PRICE,S.SITEM_AMOUNT "
					+ "FROM ITEM I, SITEM S "
					+ "WHERE SHOP_CODE=? AND I.ITEM_CODE=S.ITEM_CODE AND S.SITEM_AMOUNT!=0";
			ps = conn.prepareStatement(itemlist);
			ps.setInt(1, shop_code);
			rs = ps.executeQuery();
			List<ItemDTO> item = new ArrayList<>();
			while(rs.next()) {
				ItemDTO dto = new ItemDTO();
				dto.setItem_code(rs.getInt("item_code"));
				dto.setSitem_code(rs.getInt("sitem_code"));
				dto.setItem_name(rs.getString("item_name"));
				dto.setItem_price(rs.getInt("item_price"));
				dto.setSitem_amount(rs.getInt("sitem_amount"));
				item.add(dto);
			}
			req.setAttribute("itemlist", item);
			
			String psaleslist = "SELECT I.ITEM_NAME,I.ITEM_PRICE,P.SALES_AMOUNT "
					+ "FROM ITEM I, PSALES P, SITEM S "
					+ "WHERE P.ACCOUNT_ID=? AND P.SITEM_CODE=S.SITEM_CODE AND S.ITEM_CODE=I.ITEM_CODE";
			ps = conn.prepareStatement(psaleslist);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			List<ItemDTO> psales = new ArrayList<>();
			while(rs.next()) {
				ItemDTO dto = new ItemDTO();
				dto.setItem_name(rs.getString("item_name"));
				dto.setItem_price(rs.getInt("item_price"));
				dto.setSales_amount(rs.getInt("sales_amount"));
				psales.add(dto);
			}
			req.setAttribute("psaleslist", psales);
			
			String sales_sum = "SELECT NVL(SUM(I.ITEM_PRICE * P.SALES_AMOUNT), 0) AS SALES_SUM "
					+ "FROM ITEM I, PSALES P,SITEM S "
					+ "WHERE P.ACCOUNT_ID=? AND P.SITEM_CODE=S.SITEM_CODE AND I.ITEM_CODE=S.ITEM_CODE";
			ps = conn.prepareStatement(sales_sum);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			int sum = 0;
			if(rs.next()) {
				sum = rs.getInt("sales_sum");
			}
			req.setAttribute("sales_sum", sum);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/sales/isales.jsp");
	}

}
