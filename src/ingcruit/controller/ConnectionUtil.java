package ingcruit.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConnectionUtil {
	public static Connection getConnection() {
		Connection conn = null;
		
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "hee", password = "hee";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, userid, password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return conn;
	}
	
	public static void dbDisconnection(ResultSet rs, Statement st, Connection conn) {
		try {
			if(rs!=null) rs.close();
			if(st!=null) st.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Date converToDate(String sdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date sqlDate = null;
		try {
			java.util.Date d = sdf.parse(sdate);  //String -> Date  util의 date //util과 sql중 하나만 임포트 하고 다른하나는 풀로 써야함 
			sqlDate = new Date(d.getTime());  //sql의 date
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return sqlDate;
	}
}
