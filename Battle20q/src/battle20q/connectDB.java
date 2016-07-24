package battle20q;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringTokenizer;


public class connectDB {
	private String  DBdriver = "com.mysql.jdbc.Driver";
	private String  DBurl    = "jdbc:mysql://localhost:3306/quiz20";
	private String  DBid     = "selvia";
	private String  DBpwd    = "tmfrlfl1";
	
	private connectDBwrite lw   = new connectDBwrite();

	public connectDB() {
	}  

	/*
	 *  연결 요청
	 */
	public Connection getConnection() throws SQLException {
		
		Connection conn = null; 		
		try { 
			Class.forName(DBdriver); 
			conn = DriverManager.getConnection(DBurl, DBid, DBpwd);
		} catch(SQLException e) {
			lw.writeLog("\r\n" + "[cmnDbcon - getConnection() - SQLException]: " + e + "\r\n");
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnDbcon - getConnection() - Exception]: " + e + "\r\n");
			return null;
		}
		
		return conn; 
	} 

	/*
	 *  자동 커밋 설정
	 */
	public void setAutocommit(Connection conn, boolean bcmt) {
		try	{
			if(conn != null) { conn.setAutoCommit(bcmt); }
		} catch(SQLException e) {
			lw.writeLog("\r\n" + "[cmnDbcon - setAutocommit() - SQLException]: " + e + "\r\n");
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnDbcon - setAutocommit() - Exception]: " + e + "\r\n");
		}
	}

	/*
	 *  연결해제
	 */
	public void conClose(Connection conn) {
		try	{
			if(conn != null) { conn.close(); }
		} catch(SQLException e) {
			lw.writeLog("\r\n" + "[cmnDbcon - conClose() - SQLException]: " + e + "\r\n");
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnDbcon - conClose() - Exception]: " + e + "\r\n");
		}
	} 

	/*
	 *  커밋
	 */
	public void setCommit(Connection conn) {
		try {
			if(conn != null) { conn.commit(); }
		} catch(SQLException e) {
			lw.writeLog("\r\n" + "[cmnDbcon - setCommit() - SQLException]: " + e + "\r\n");
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnDbcon - setCommit() - Exception]: " + e + "\r\n");
		}
	}  
	
	/*
	 *  롤백
	 */
	public void setRollback(Connection conn) {
		try {
			if(conn != null) { conn.rollback(); }
		} catch(SQLException e) {
			lw.writeLog("\r\n" + "[cmnDbcon - setRollback() - SQLException]: " + e + "\r\n");
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnDbcon - setRollback() - Exception]: " + e + "\r\n");
		}
	}  

}
