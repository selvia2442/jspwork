package battle20q;

import java.sql.*;
import java.util.*;

public class connectListbean {
	private cmnLogwrite lw = new cmnLogwrite();  // ����� �� ����ó���� ���� ������� ������
	private int realRowcnt = 0;                  // ���� Display�� ������ Row��    
	private int Totalrows  = 0;                  // ��ü ������ Row��   
	private String error_msg = "";
	private boolean is_error = false;
 
	/*
	 *  �� ���ڵ�� ��ȸ
	 */
	public int getTotalrows(Connection conn, String query) {
		
		Totalrows = 0; 
		try {
			PreparedStatement psmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);					
			ResultSet rs =  psmt.executeQuery();
			
			rs.next();
			Totalrows = rs.getInt(1);
				
			rs.close();
			psmt.close();
		} catch(SQLException e) {
			lw.writeLog("\r\n" + "[cmnListbean - getTotalrows() - SQLException]: " + e + "\r\n");
			return 0;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - getTotalrows() - Exception]: " + e + "\r\n");
			return 0;
		}	
		
		return Totalrows;
	}
	
	/*
	 *  �� ���ڵ�� ��ȸ
	 */
	public int getTotalrows() {
		return Totalrows;
	}

	/*
	 *  ���������� ���� Display�� ������ Row�� 
	 */
	public int getRealrows() {
		return realRowcnt;
	}
	
	public boolean isError()
	{
		return is_error;
	}
	
	public String getErrorMessage()
	{
		if(is_error) return error_msg;
		else return "";
	}

	/*
	 *  �������忡 ���� �ش� ��������ü�� Hashtable�� ���� -- �� ���ڵ�� ��ȸ���
	 *  (Connection, ������)
	 */
	public Hashtable getAllcnt(Connection conn, String query) {
		
		Hashtable alldata = new Hashtable();		
		int index = 0;
		Totalrows = 0; 
		is_error = false;
		error_msg = "";
		
		try {  
			PreparedStatement psmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);   					
			ResultSet rs = psmt.executeQuery();
			ResultSetMetaData rsmeta = rs.getMetaData();
		
			while(rs.next()) {   // �� ���ڵ�� ��ȸ
				Totalrows++; 
			}

			rs.beforeFirst();

			int count = 0;
			
			while(rs.next()) {
				for (int cols_num = 1; cols_num <= rsmeta.getColumnCount(); cols_num++)	 {
					alldata.put(new Integer(count), getData(rsmeta.getColumnType(cols_num),rs,cols_num));
					count++;
				}	
				index++;
			}

			rs.close();
			psmt.close();
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - getAllcnt() - SQLException]: " + e + "\r\n");
			is_error = true;
			error_msg = e.getMessage();
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - getAllcnt() - Exception]: " + e + "\r\n");
			is_error = true;
			error_msg = e.getMessage();
			return null;
		}
		
		return alldata;
	}
	
	/*
	 *  �������忡 ���� �ش� ��������ü�� Hashtable�� ����
	 *  (Connection, ������)
	 */
	public Hashtable getAll(Connection conn, String query) {
		
		Hashtable alldata = new Hashtable();		
		int index = 0;
		
		try {
			PreparedStatement psmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);					
			ResultSet rs = psmt.executeQuery();
			ResultSetMetaData rsmeta = rs.getMetaData();
		
			int count = 0;
		
			while(rs.next()) {
				for (int cols_num = 1; cols_num <= rsmeta.getColumnCount(); cols_num++)	 {
					alldata.put(new Integer(count), getData(rsmeta.getColumnType(cols_num),rs,cols_num));
					count++;
				}	
				index++;
			}

			rs.close();
			psmt.close();
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - getAll() - SQLException]: " + e + "\r\n");
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - getAll() - Exception]: " + e + "\r\n");
			return null;
		}
		
		return alldata;
	}

	/*
	 *  �������忡 ���� �ش� ������ �����͸� Hashtable�� ���� -- �� ���ڵ�� ��ȸ���
	 *  (Connection, ������, ����������, ���������� ��·��ڵ��)
	 */
	public Hashtable getListcnt(Connection conn, String query, int CurrPage, int PageLine) {
		
		Hashtable listdata = new Hashtable();		
		int index = 0;
		Totalrows = 0; 
		
		try {
			PreparedStatement psmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);					
			ResultSet rs = psmt.executeQuery();
			ResultSetMetaData rsmeta = rs.getMetaData();

			while(rs.next()) {   // �� ���ڵ�� ��ȸ
				Totalrows++; 
			}
			
			rs.beforeFirst();

			for (int i = 0; i < (CurrPage*PageLine - PageLine); i++) {
				rs.next(); 
			}
		
			int count = 0;
		
			while(rs.next() && index < PageLine) {
				for (int cols_num = 1; cols_num <= rsmeta.getColumnCount(); cols_num++)	 {
					listdata.put(new Integer(count), getData(rsmeta.getColumnType(cols_num),rs,cols_num));
					count++;
				}	
				index++;
			}

			realRowcnt = index;   // ���������� ���� Display�� ������ Row ����
			
			rs.close();
			psmt.close();
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - getListcnt() - SQLException]: " + e + "\r\n");
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - getListcnt() - Exception]: " + e + "\r\n");
			return null;
		}
		
		return listdata;
	}

	/*
	 *  �������忡 ���� �ش� ������ �����͸� Hashtable�� ����
	 *  (Connection, ������, ����������, ���������� ��·��ڵ��)
	 */
	public Hashtable getList(Connection conn, String query, int CurrPage, int PageLine) {
		
		Hashtable listdata = new Hashtable();		
		int index = 0;
		
		try {
			PreparedStatement psmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);					
			ResultSet rs = psmt.executeQuery();
			ResultSetMetaData rsmeta = rs.getMetaData();

			for (int i = 0; i < (CurrPage*PageLine - PageLine); i++) {
				rs.next(); 
			}
			
		
			int count = 0;
		
			while(rs.next() && index < PageLine) {
				for (int cols_num = 1; cols_num <= rsmeta.getColumnCount(); cols_num++)	 {
					listdata.put(new Integer(count), getData(rsmeta.getColumnType(cols_num),rs,cols_num));
					count++;
				}	
				index++;
			}

			realRowcnt = index;   // ���������� ���� Display�� ������ Row ����
			
			rs.close();
			psmt.close();
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - getList() - SQLException]: " + e + "\r\n");
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - getList() - Exception]: " + e + "\r\n");
			return null;
		}
		
		return listdata;
	}

	/*
	 *  ��ȸ�� ������ ����   java.sql.Types ���� TypeȮ��
	 */
	public String getData(int columntype, ResultSet rs,int cols_num) {
		
		try {
			if(columntype == 4 || columntype == 5) { // ���� Type �� ���
				int intval = getIntValue(rs,cols_num);
			
				if(new Integer(intval).toString().equals("")) return "";
				else return new Integer(intval).toString();
			} else if (columntype == 3 || columntype == 2 || columntype == -5) {
				return (String) rs.getBigDecimal(cols_num).toString();			
			} else if(columntype == 12 || columntype == 1 || columntype == -1) {  // ���� Type�� ��� �ѱۺ�ȯ �ʿ����� Ȯ���� �����
				if(rs.getString(cols_num).equals("")) return "";
				else return cmnUtil.StringReplace(rs.getString(cols_num),"\"", "&quot;");
			} else if(columntype == 93 || columntype == 91) {
				String str = rs.getDate(cols_num).toString();
				return str;
			} else if(columntype == 8)
			{
				double intval = getDoubleValue(rs,cols_num);		
				if(new Double(intval).toString().equals("")) return "";
				else return new Double(intval).toString();
			}
			
			return "colum-type: " + columntype;
		} catch(Exception e) {
			return "";
		}
	}

	public int getIntValue(ResultSet rs, int cols_num) {
		
		int intval = 0;

		try	{
			if (cols_num != 0) {
				intval = rs.getInt(cols_num);
				return intval;
			}
			
			intval = 0;
			return intval;

		} catch(Exception e) {
			return 0;
		}
	}	

	public double getDoubleValue(ResultSet rs, int cols_num) {
		
		double intval = 0;

		try	{
			if (cols_num != 0) {
				intval = rs.getDouble(cols_num);
				return intval;
			}
			
			intval = 0;
			return intval;

		} catch(Exception e) {
			return 0;
		}
	}	

	/*
	 *  insert, update, delete ��Ʈ������ �ٷ� ó�� 
	 */
	public boolean executeQuery(Connection conn, String query) {
		
		try {
			PreparedStatement psmt = conn.prepareStatement(query);					
			psmt.executeUpdate();

			psmt.close();
			return true;
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - executeQuery() - SQLException]: " + e + "\r\n");
			return false;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - executeQuery() - Exception]: " + e + "\r\n");
			return false;
		} 
	}

	/*
	 *  insert, update, delete PreparedStatement ó��
	 */
	public PreparedStatement setPStmt(Connection conn, String query) {
		
		try {
			PreparedStatement psmt = conn.prepareStatement(query);					
			return psmt;
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - setPStmt() - SQLException]: " + e + "\r\n");
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - setPStmt() - Exception]: " + e + "\r\n");
			return null;
		}
	}
	
	/*
	 *  insert, update, delete PreparedStatement ó��
	 */
	public PreparedStatement setPStmt(Connection conn, String query, int autoGeneratedKeys ) {
		
		try {
			PreparedStatement psmt = conn.prepareStatement(query, autoGeneratedKeys);					
			return psmt;
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - setPStmt() - SQLException]: " + e + "\r\n");
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - setPStmt() - Exception]: " + e + "\r\n");
			return null;
		}
	}

	/*
	 *  procedure CallableStatement ó��
	 */
	public CallableStatement setCStmt(Connection conn, String query) {
		
		try {
			CallableStatement csmt = conn.prepareCall(query); 					
			return csmt;
		} catch (SQLException e) { 
			lw.writeLog("\r\n" + "[cmnListbean - setCStmt() - SQLException]: " + e + "\r\n");
			return null;
		} catch(Exception e) {
			lw.writeLog("\r\n" + "[cmnListbean - setCStmt() - Exception]: " + e + "\r\n");
			return null;
		}
	}
}
