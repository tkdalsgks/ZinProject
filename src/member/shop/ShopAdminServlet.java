package member.shop;

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

import shop.shopDTO.ShopDTO;
import util.DBConnection;
import util.MyServlet;

@WebServlet("/shopadmin")
public class ShopAdminServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		String SQL = "SELECT COMPANY_CODE FROM ACCOUNT WHERE ACCOUNT_ID=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int company_code = rs.getInt("company_code");
			
			SQL = "SELECT MEMBER_CODE FROM MEMBER WHERE ACCOUNT_ID=?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int member_code = rs.getInt("member_code");
			
			
			String shoplist = "SELECT * FROM SHOP WHERE COMPANY_CODE=? AND MEMBER_CODE=?";
			ps = conn.prepareStatement(shoplist);
			ps.setInt(1, company_code);
			ps.setInt(2, member_code);
			rs = ps.executeQuery();
			List<ShopDTO> shop = new ArrayList<>();
			while(rs.next()) {
				ShopDTO dto = new ShopDTO();
				dto.setShop_code(rs.getInt("shop_code"));
				dto.setCompany_code(rs.getInt("company_code"));
				dto.setMember_code(rs.getInt("member_code"));
				dto.setShop_name(rs.getString("shop_name"));
				dto.setAccount_id(rs.getString("account_id"));
				shop.add(dto);
			}
			req.setAttribute("shoplist", shop);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/shop/shopadmin.jsp");
	}

}
