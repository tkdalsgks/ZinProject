package Menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/menulist")
public class Menulist extends HttpServlet {
	String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
	String id = "khw";
	String pw = "khwpw";
      
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		String account_id = req.getParameter("account_id");
		
		Connection conn = null;
		
		String SQL = "select count(*) as cnt from shop where account_id=?";
		//System.out.println(account_id);
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			
			ps = conn.prepareStatement(SQL);
			ps.setString(1, account_id);
			
			rs = ps.executeQuery();
			rs.next();
			int result = rs.getInt("cnt");
			String menulist;
			if(result == 0) {  // 본사직원인 경우
				menulist = "select * from tmpmenu where menu_access='A' or menu_access='M' and menu_top=0";
				//System.out.println("본사");
				
			}else {            // 점포주인인 경우
				menulist = "select * from tmpmenu where menu_access='A' or menu_access='S' and menu_top=0";
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
			
			
			
		} catch(ClassNotFoundException e) {
			System.out.println("Driver 미설치 또는 드라이버이름 오류");
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
