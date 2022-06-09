package member.sales;

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import menu.menuDTO.MenuDTO;
import shop.item.itemDTO.ItemDTO;
import shop.shopDTO.ShopDTO;
import util.DBConnection;
import util.MyServlet;

@WebServlet("/shoplist")
public class ShopListServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String account_id = (String)session.getAttribute("account_id");
		
		try {
			
			String memberCode = "SELECT MEMBER_CODE FROM MEMBER WHERE ACCOUNT_ID=?";
			
			ps = conn.prepareStatement(memberCode);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int member_code = rs.getInt("MEMBER_CODE");
			
			String shopList = "SELECT * FROM SHOP WHERE MEMBER_CODE=? ORDER BY SHOP_NAME";			
			ps = conn.prepareStatement(shopList);
			ps.setInt(1, member_code);
			rs = ps.executeQuery();
			
			List<ShopDTO> shop = new ArrayList<>();
			
			while(rs.next()) {
				ShopDTO dto = new ShopDTO();
				dto.setShop_code(rs.getInt("SHOP_CODE"));
				dto.setCompany_code(rs.getInt("COMPANY_CODE"));
				dto.setMember_code(rs.getInt("MEMBER_CODE"));
				dto.setShop_name(rs.getString("SHOP_NAME"));
				shop.add(dto);
			}
			/*if(shop.isEmpty()) {
				ShopDTO dto = new ShopDTO();
				String companyCode = "SELECT COMPANY_CODE FROM ACCOUNT WHERE ACCOUNT_ID=?";
				ps1 = conn.prepareStatement(companyCode);
				ps1.setString(1, account_id);
				rs1 = ps1.executeQuery();
				rs1.next();
				int company_code = rs1.getInt("COMPANY_CODE");
				dto.setCompany_code(company_code);
				dto.setMember_code(0);
				shop.add(dto);
			}*/
			
			Gson gson= new Gson();
			String value = gson.toJson(shop);
			out.print(value);
			
			
			
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
