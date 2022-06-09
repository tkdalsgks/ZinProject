package member.sales;

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

import member.item.itemDTO.ItemDTO;
import member.sales.payDTO.PayDTO;
import member.sales.salesDTO.SalesDTO;
import shop.shopDTO.ShopDTO;
import util.DBConnection;
import util.MyServlet;


@WebServlet("/sales")
public class SalesServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.forward(req, resp, "/WEB-INF/views/sales/sales.jsp");
	}

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection conn = DBConnection.getConnection();
		resp.setContentType("text/html;charset=UTF-8");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		HttpSession session = req.getSession();
		String account_id = (String)session.getAttribute("account_id");
		req.setCharacterEncoding("UTF-8");
		
		String shop_code = req.getParameter("shop_code");
		
		
		try {
			List<Integer> shoplist = new ArrayList<>();
			
			if(shop_code.equals("all")) {  // 본사 전체 점포
				String CompanyCode = "SELECT COMPANY_CODE FROM ACCOUNT WHERE ACCOUNT_ID=?";
				ps = conn.prepareStatement(CompanyCode);
				ps.setString(1, account_id);
				rs = ps.executeQuery();
				rs.next();
				int company_code = rs.getInt("COMPANY_CODE");
				
				String ShopList = "SELECT SHOP_CODE FROM SHOP WHERE COMPANY_CODE=?";
				ps = conn.prepareStatement(ShopList);
				ps.setInt(1, company_code);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					shoplist.add(rs.getInt("SHOP_CODE")); 
				}
				
			}else if(shop_code.equals("memall")) { // 관리 점포 전체
				String MemberCode = "SELECT MEMBER_CODE FROM MEMBER WHERE ACCOUNT_ID=?";
				ps = conn.prepareStatement(MemberCode);
				ps.setString(1, account_id);
				rs = ps.executeQuery();
				rs.next();
				int member_code = rs.getInt("MEMBER_CODE");
				
				String ShopList = "SELECT SHOP_CODE FROM SHOP WHERE MEMBER_CODE=?";
				ps = conn.prepareStatement(ShopList);
				ps.setInt(1, member_code);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					shoplist.add(rs.getInt("SHOP_CODE")); 
				}
				
			}else {
				shoplist.add(Integer.parseInt(shop_code));
			}
			
			
			// 공통 코드
			
			List<Integer> sitemlist = new ArrayList<>();
			for(int i=0;i<shoplist.size();i++) {
				String sitemList = "SELECT SITEM_CODE FROM SITEM WHERE SHOP_CODE=?";
				ps = conn.prepareStatement(sitemList);
				ps.setInt(1, shoplist.get(i));
				rs = ps.executeQuery();
				while(rs.next()) {
					sitemlist.add(rs.getInt("SITEM_CODE"));
				}
			}
			List<SalesDTO> saleslist = new ArrayList<>();
			for(int i=0;i<sitemlist.size();i++) {					
				String salesList = "SELECT * FROM SALES WHERE SITEM_CODE=?";
				ps = conn.prepareStatement(salesList);
				ps.setInt(1, sitemlist.get(i));
				rs = ps.executeQuery();
				while(rs.next()) {
					SalesDTO salesdto = new SalesDTO();
					salesdto.setPay_code(rs.getInt("PAY_CODE"));
					salesdto.setSales_amount(rs.getInt("SALES_AMOUNT"));
					salesdto.setSales_code(rs.getInt("SALES_CODE"));
					salesdto.setSales_date(rs.getDate("SALES_DATE"));
					salesdto.setSales_number(rs.getInt("SALES_NUMBER"));
					salesdto.setSales_price(rs.getInt("SALES_PRICE"));
					salesdto.setSales_sort(rs.getInt("SALES_SORT"));
					salesdto.setSitem_code(rs.getInt("SITEM_CODE"));
					
					String itemCode = "SELECT ITEM_CODE FROM SITEM WHERE SITEM_CODE=?";
					ps1 = conn.prepareStatement(itemCode);
					ps1.setInt(1, salesdto.getSitem_code());
					rs1 = ps1.executeQuery();
					rs1.next();
					int item_code = rs1.getInt("ITEM_CODE");
					String itemName = "SELECT ITEM_NAME FROM ITEM WHERE ITEM_CODE=?";
					ps1 = conn.prepareStatement(itemName);
					ps1.setInt(1, item_code);
					rs1 = ps1.executeQuery();
					rs1.next();
					String item_name = rs1.getString("ITEM_NAME");
					salesdto.setItem_code(item_code);
					salesdto.setItem_name(item_name);
					saleslist.add(salesdto);

				}
			}
			
			String payList = "SELECT * FROM PAY";
			List<PayDTO> paylist= new ArrayList<>();
			ps = conn.prepareStatement(payList);
			rs = ps.executeQuery();
			while(rs.next()) {
				PayDTO paydto = new PayDTO();
				paydto.setCompany_code(rs.getInt("COMPANY_CODE"));
				paydto.setPay_code(rs.getInt("PAY_CODE"));
				paydto.setPay_name(rs.getString("PAY_NAME"));
				paylist.add(paydto);
			}
			
			req.setAttribute("paylist", paylist);
			req.setAttribute("saleslist", saleslist);
			
			
			
			
		} catch(SQLException e) {
			System.out.println("DB 접속 오류거나 SQL 문장 오류");
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
		
		super.forward(req, resp, "/WEB-INF/views/sales/sales.jsp");
	}

}
