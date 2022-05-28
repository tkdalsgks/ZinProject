package Account;

import java.sql.DriverManager;
import java.sql.SQLException;

public class a {
//	// company
//	try {
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		conn = DriverManager.getConnection(url, id, pw);
//		
//		ps = conn.prepareStatement(company_SQL);
//		ps.setInt(1, company_code);
//		ps.setString(2, company_name);
//		insert = ps.executeUpdate();
//		
//	} catch(ClassNotFoundException e) {
//		System.out.println("Driver 미설치 또는 드라이버이름 오류");
//	} catch(SQLException e) {
//		System.out.println("DB 접속 오류거나 SQL 문장 오류");
//		e.printStackTrace();
//	} finally {
//		try {
//			conn.close();
//		} catch(SQLException e) {}
//	}
//	
//	// team
//	try {
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		conn = DriverManager.getConnection(url, id, pw);
//		
//		ps = conn.prepareStatement(team_SQL);
//		ps.setInt(1, team_code);
//		ps.setInt(2, company_code);
//		ps.setString(3, team_name);
//		insert = ps.executeUpdate();
//		
//	} catch(ClassNotFoundException e) {
//		System.out.println("Driver 미설치 또는 드라이버이름 오류");
//	} catch(SQLException e) {
//		System.out.println("DB 접속 오류거나 SQL 문장 오류");
//		e.printStackTrace();
//	} finally {
//		try {
//			conn.close();
//		} catch(SQLException e) {}
//	}
//	
//	// member
//	try {
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		conn = DriverManager.getConnection(url, id, pw);
//		
//		ps = conn.prepareStatement(member_SQL);
//		ps.setInt(1, member_code);
//		ps.setInt(2, team_code);
//		ps.setString(3, member_name);
//		ps.setString(4, account_id);
//		insert = ps.executeUpdate();
//		
//	} catch(ClassNotFoundException e) {
//		System.out.println("Driver 미설치 또는 드라이버이름 오류");
//	} catch(SQLException e) {
//		System.out.println("DB 접속 오류거나 SQL 문장 오류");
//		e.printStackTrace();
//	} finally {
//		try {
//			conn.close();
//		} catch(SQLException e) {}
//	}
//	
//	// shop
//	try {
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		conn = DriverManager.getConnection(url, id, pw);
//		
//		ps = conn.prepareStatement(shop_SQL);
//		ps.setInt(1, shop_code);
//		ps.setInt(2, company_code);
//		ps.setInt(3, member_code);
//		ps.setString(4, shop_name);
//		ps.setString(5, account_id);
//		insert = ps.executeUpdate();
//		
//	} catch(ClassNotFoundException e) {
//		System.out.println("Driver 미설치 또는 드라이버이름 오류");
//	} catch(SQLException e) {
//		System.out.println("DB 접속 오류거나 SQL 문장 오류");
//		e.printStackTrace();
//	} finally {
//		try {
//			conn.close();
//		} catch(SQLException e) {}
//	}
}
