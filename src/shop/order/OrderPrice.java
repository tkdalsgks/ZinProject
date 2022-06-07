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

@WebServlet("/orderprice")
public class OrderPrice extends MyServlet {
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		req.setCharacterEncoding("UTF-8");
		String item_code = req.getParameter("item_code");
		//System.out.println(item_code);
		
		try {
			
			String ItemPrice = "SELECT ITEM_PRICE FROM ITEM WHERE ITEM_CODE=?";
			
			ps = conn.prepareStatement(ItemPrice);
			ps.setInt(1, Integer.parseInt(item_code));
			rs = ps.executeQuery();
			rs.next();
			int item_price = rs.getInt("ITEM_PRICE");
			//System.out.println(item_price);
			out.print(item_price);
			
			
			
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
