package member.item;

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

@WebServlet("/iteminsert")
public class ItemInsertServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		
		String SQL = "select member_name from member where account_id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			String member_name = rs.getString("member_name");
			
			
			String companyCode = "select p.company_code from company p, team t, member m where m.account_id=? and m.team_code = t.team_code and t.company_code=p.company_code";
			ps = conn.prepareStatement(companyCode);
			ps.setString(1, account_id);
			rs = ps.executeQuery();
			rs.next();
			int company_code = rs.getInt("company_code");
			
			req.setAttribute("member_name", member_name);
			req.setAttribute("company_code", company_code);
			
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/item/iteminsert.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		String item_name = req.getParameter("item_name");
		int company_code = Integer.parseInt(req.getParameter("company_code"));
		int item_price = Integer.parseInt(req.getParameter("item_price"));
		String account_id = (String)session.getAttribute("account_id");
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String itemCode = "select max(item_code) as item_code from item";
			ps = conn.prepareStatement(itemCode);
			rs = ps.executeQuery();
			rs.next();
			int item_code = rs.getInt("item_code") + 1;
			
			String insertItem = "insert into item(item_code,company_code,item_name,item_price) values(?,?,?,?)";
			ps = conn.prepareStatement(insertItem);
			ps.setInt(1, item_code);
			ps.setInt(2, company_code);
			ps.setString(3, item_name);
			ps.setInt(4, item_price);
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
		resp.sendRedirect(cPath + "/itemadmin");
	}

}
