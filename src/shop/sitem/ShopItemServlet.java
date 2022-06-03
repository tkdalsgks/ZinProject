package shop.sitem;

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
		
		String SQL = "select shop_code from shop where account_id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int shop_code = rs.getInt("shop_code");
			
			String itemlist = "select * from sitem where shop_code=? order by sitem_code";
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
				sitem.add(dto);
			}
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
