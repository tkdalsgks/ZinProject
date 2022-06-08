package member.item;

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

import member.item.itemDTO.ItemDTO;
import util.DBConnection;
import util.MyServlet;


@WebServlet("/itemadmin")
public class ItemAdminServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		PrintWriter out = resp.getWriter();
		
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
			
			String itemlist = "SELECT * FROM ITEM WHERE COMPANY_CODE=? ORDER BY ITEM_SORT,ITEM_CODE";
			ps = conn.prepareStatement(itemlist);
			ps.setInt(1, company_code);
			rs = ps.executeQuery();
			List<ItemDTO> item = new ArrayList<>();
			while(rs.next()) {
				ItemDTO dto = new ItemDTO();
				dto.setCompany_code(rs.getInt("company_code"));
				dto.setItem_code(rs.getInt("item_code"));
				dto.setItem_name(rs.getString("item_name"));
				dto.setItem_price(rs.getInt("item_price"));
				dto.setItem_sort(rs.getInt("item_sort"));
				item.add(dto);
			}
			
			req.setAttribute("itemlist", item);
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/item/itemadmin.jsp");
	}

}
