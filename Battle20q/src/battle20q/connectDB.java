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
	 *  ���� ��û
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
	 *  �ڵ� Ŀ�� ����
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
	 *  ��������
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
	 *  Ŀ��
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
	 *  �ѹ�
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
