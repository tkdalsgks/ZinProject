package shop.sitem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.sitem.sitemDTO.SitemDTO;
import util.DBConnection;
import util.MyServlet;

@WebServlet("/shopitem")
public class ShopItemServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		PrintWriter out = resp.getWriter();
		
		String SQL = "SELECT SHOP_CODE FROM SHOP WHERE ACCOUNT_ID=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int shop_code = rs.getInt("shop_code");
			
			String itemlist = "SELECT * FROM SITEM WHERE SHOP_CODE=? AND SITEM_AMOUNT != 0";
			ps = conn.prepareStatement(itemlist);
			ps.setInt(1, shop_code);
			rs = ps.executeQuery();
			List<SitemDTO> sitem = new ArrayList<>();
			while(rs.next()) {
				SitemDTO dto = new SitemDTO();
				dto.setSitem_code(rs.getInt("sitem_code"));
				dto.setShop_code(rs.getInt("shop_code"));
				dto.setItem_code(rs.getInt("item_code"));
				dto.setSitem_amount(rs.getInt("sitem_amount"));
				
				String itemName = "SELECT ITEM_NAME FROM ITEM WHERE ITEM_CODE=?";
				ps2 = conn.prepareStatement(itemName);
				ps2.setInt(1, dto.getItem_code());
				rs2 = ps2.executeQuery();
				rs2.next();
				String item_name = rs2.getString("ITEM_NAME");
				dto.setItem_name(item_name);
				sitem.add(dto);
				
			}
			
			Collections.sort(sitem, new Comparator<SitemDTO>() {
				
				@Override
				public int compare(SitemDTO dto1, SitemDTO dto2) {
					return dto1.getItem_name().compareTo(dto2.getItem_name());
				}
			});
			
			req.setAttribute("sitemlist", sitem);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/shop/shopitem.jsp");
	}

}
