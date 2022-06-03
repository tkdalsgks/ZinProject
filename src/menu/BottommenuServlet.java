package menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
import util.DBConnection;
import util.MyServlet;

/**
 * Servlet implementation class Bottommenu
 */
@WebServlet("/bottommenu")
public class BottommenuServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String menu_top = req.getParameter("menu_top");
		
		String SQL = "SELECT * FROM TMPMENU WHERE MENU_TOP=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, Integer.parseInt(menu_top));
			
			rs = ps.executeQuery();
			
			List<MenuDTO> menu = new ArrayList<>();
			
			while(rs.next()) {
				MenuDTO dto = new MenuDTO();
				dto.setCompany_code(rs.getInt("company_code"));
				dto.setMenu_code(rs.getInt("menu_code"));
				dto.setMenu_name(rs.getString("menu_name"));
				dto.setMenu_top(rs.getInt("menu_top"));
				dto.setMenu_access(rs.getString("menu_access"));
				dto.setMenu_url(rs.getString("menu_url"));
				menu.add(dto);
			}
			
			Gson gson= new Gson();
			String value = gson.toJson(menu);
			//System.out.println(menu_top +"번째 메뉴 mouseover");
			//System.out.println(value);
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
