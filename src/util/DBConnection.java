package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection dbConn;

	public static Connection getConnection() {
		Connection conn = null;
		
		String url = "jdbc:oracle:thin:@168.126.28.44:1521:PKGORCL";
		String id = "khw";
		String pw = "khwpw";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
		return conn;
	}
}
