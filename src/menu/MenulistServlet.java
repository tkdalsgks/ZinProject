package menu;

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
import util.DBConnection;

@WebServlet("/menulist")
public class MenulistServlet extends HttpServlet {
      
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String account_id = req.getParameter("account_id");
		
		String SQL = "SELECT COUNT(*) AS CNT FROM SHOP WHERE ACCOUNT_ID=?";
		//System.out.println(account_id);
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		try {
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int result = rs.getInt("cnt");
			String menulist;
			if(result == 0) {  // 본사직원인 경우
				menulist = "SELECT * FROM TMPMENU WHERE MENU_ACCESS='A' OR MENU_ACCESS='M' AND MENU_TOP=0";
				//System.out.println("본사");
				
			}else {            // 점포주인인 경우
				menulist = "SELECT * FROM TMPMENU WHERE MENU_ACCESS='A' OR MENU_ACCESS='S' AND MENU_TOP=0";
				//System.out.println("점포");
			}
			ps = conn.prepareStatement(menulist);
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
