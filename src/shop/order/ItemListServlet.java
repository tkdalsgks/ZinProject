package shop.order;

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
import util.DBConnection;
import util.MyServlet;

@WebServlet("/itemlist")
public class ItemListServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String account_id = (String)session.getAttribute("account_id");
		
		try {
			
			String companyCode = "SELECT COMPANY_CODE FROM ACCOUNT WHERE ACCOUNT_ID=?";
			
			ps = conn.prepareStatement(companyCode);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int company_code = rs.getInt("COMPANY_CODE");
			
			String itemList = "SELECT * FROM ITEM WHERE COMPANY_CODE=? AND ITEM_SORT!=1 ORDER BY ITEM_NAME";			
			ps = conn.prepareStatement(itemList);
			ps.setInt(1, company_code);
			rs = ps.executeQuery();
			
			List<ItemDTO> item = new ArrayList<>();
			
			while(rs.next()) {
				ItemDTO dto = new ItemDTO();
				dto.setItem_code(rs.getInt("ITEM_CODE"));
				dto.setCompany_code(rs.getInt("COMPANY_CODE"));
				dto.setItem_name(rs.getString("ITEM_NAME"));
				dto.setItem_price(rs.getInt("ITEM_PRICE"));
				item.add(dto);
			}
			
			Gson gson= new Gson();
			String value = gson.toJson(item);
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
