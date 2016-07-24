package battle20q;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class cmnUtil {
/* ========================================================================================== */
	
	/*
	 *  ���ڿ� ����
	 *  (str1 : ���� ���ڿ�, str2 : �ٲپ�� �� ���ڿ�, str3 : �ٲ� ���ڿ�)
	 */
	public static String StringReplace(String str1, String str2, String str3) {
		
		StringBuffer strbf = new StringBuffer(str1);
		int index = 0;
		while(true) {
			index = str1.indexOf(str2, index);
			if( index == -1 ) break;
			strbf = strbf.replace( index, index + str2.length(), str3);
			str1 = strbf.toString();
			index += str3.length();
		}
		return str1;
	}

	/*
	 *  ���ڿ� �ִ��� Ȯ��
	 *  (str1 : ���� ���ڿ�, str2 : Ȯ���� ���ڿ�)
	 */
	public static boolean FindString(String str1, String str2) {
		
		int index = 0;
		while(true) {
			index = str1.indexOf(str2, index);
			if( index == -1 ) return false;
			else return true;
		}
	} 
	
	
	public static String encodingChg(String str, String from, String to)
	{
		try {
			return new String(str.getBytes(from), to);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	/*
	 *  NULL���� ���̳� ������ ������ ����
	 */
	public static String NVL(String str, String nvlstr) {
    	if(str == null || str.trim().equals("") || str.trim().equals("null"))
    		return nvlstr;
    	else
    		return str;
	}
	
	/*
	 *  NULL���� ���̳� ������ ������ ����
	 */
	public static String[] NVL(String[] strs, String nvlstr) {
		if(strs==null) return new String[0];
		for(int i =0;i<strs.length;i++)
		{
			if(strs[i].trim().equals("") || strs[i].trim().equals("null"))
				strs[i] = nvlstr;
		}
    	return strs;
	}

	/*
	 *  NULL���� ���̳� ������ ������ ���� (Integer��)
	 */
	public static int NVL(String str, int nvlint) {
		if(str == null || str.trim().equals("") || str.trim().equals("null"))
			return nvlint;
		else
			return Integer.parseInt(str);
	}
	
	/*
	 *  NULL���� ���̳� ������ ������ ���� (Integer��)
	 */
	public static int[] NVL(String[] strs, int nvlint) {
		if(strs==null) return new int[0];
		int[] rets = new int[strs.length];
		for(int i =0;i<strs.length;i++)
		{
			if(strs[i].trim().equals("") || strs[i].trim().equals("null"))
				rets[i] = nvlint;
			else
				rets[i] = Integer.parseInt(strs[i]);
		}
    	return rets;
	}
	
	
    
	/*
	 *  ���ڿ����� Ư������ ����
	 */
	public static String removeChars(String str, char rmChar) {
		
		int i = 0;
		while(true ) {
			i = str.indexOf(rmChar);
			if( i == -1 ) return str;
			
			str = str.substring(0, i) + str.substring(i + 1, str.length());
		}	
	}

	/*
	 *  System Error�߻��� ', " char����
	 */
	public static String removeChars(String str) {
		
		String tmp_str = str;
		tmp_str = removeChars(tmp_str, '\'');
		tmp_str = removeChars(tmp_str, '"');
		return tmp_str;
	} 

	/*
	 *  Number format���� ����
	 */
	public static String numberformat(String str) {
		try {
			
			return NumberFormat.getInstance().format(Long.parseLong(str));
		} catch (Exception e) {
			return str;
		}
	}

   /* 
	* ��Ʈ�� �ڸ��� 
	* ������ ������ ���� ��ŭ ��ĭ(" ")�� ��Ʈ���� ���Ѵ�. 
	* ���ܵ� String�� ����Ʈ ���� �ڸ� ����Ʈ ������ ���� �ʵ��� �Ѵ�. 
	* 
	* @param str ���� String 
	* @param int �ڸ� ����Ʈ ���� 
	* @param char + or -  (�ѱ��� 2bytes�̹Ƿ� ���� 1����Ʈó���� ����)
	* @param tail ���̰� �� str�� ���� �ڿ� ���� ������ 
	* @return String ���ܵ� String 
	*/ 
	public static String cutStr(String str, int length, char type, String tail) { 
		byte[] bytes = str.getBytes(); 
		int len = bytes.length; 
		int counter = 0; 

		if (length >= len) { 
			StringBuffer sb = new StringBuffer(); 
			sb.append(str); 
			for (int i = 0; i < length - len; i++) { 
				sb.append(' '); 
			} 
			return sb.toString(); 
		} 

		for (int i = length - 1; i >= 0; i--) { 
			if (((int)bytes[i] & 0x80) != 0) 
			counter++; 
		} 

		String f_str = null; 
		if (type == '+') { 
			f_str = new String(bytes, 0, length + (counter % 2)); 
		} else if (type == '-') { 
			f_str = new String(bytes, 0, length - (counter % 2)); 
		}else { 
			f_str = new String(bytes, 0, length - (counter % 2)); 
		} 

		return f_str + tail; 
	}

	public static String getsysdatetime() { 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); 
		String today = formatter.format(new java.util.Date()); 

		return today;
	}

	/* ========================================================================================== */
	
	/*
	 *  ȭ����� ���� �޼��� ����� ���ؼ� ��ũ��Ʈ Alertâ ǥ��
	 */
	public static void msgAlert(PrintWriter out, String msg) {
		out.println("<script> alert('" + msg + "'); </script>");
	}

	/*
	 *  ���� �������� ����
	 */
	public static void historyBack(PrintWriter out)	{
		out.println("<html><head><script> history.back() ; </script></head><body></body></html>");
	}
	
	/*
	 *  ó�� �� ���ϴ� �������� �̵�
	 */
	public static void loadPage(PrintWriter out, String page) {
		String script = "<html><head><script>location = \"" + page + "\";</script></head><body></body></html>";
		out.println(script);
	}

	/*
	 *  ó�� �� ���ϴ� �������� �̵�
	 */
	public static void parentloadPage(PrintWriter out, String page) {
		String script = "<html><head><script>parent.location = \"" + page + "\";</script></head><body></body></html>";
		out.println(script);
	}
	
	public static String getErrorMessage(int result_code, int order, String error_msg)
	{
		String strOrder = "";
		if(order!=0) strOrder="("+Integer.toString(order)+")";
		if(error_msg==null) error_msg = "";
		if(error_msg.length()!=0) error_msg = "=>"+error_msg;
		switch(result_code)
		{
		case 0: return "����";
		case -1: return "�� �� ���� ���Դϴ�.";

		case -7: return "�α����� �����Ͽ����ϴ�.";
		
		case -97: return "���Ͼ��ε忡 �����Ͽ����ϴ�(5M �̻��� ������ ���ε� �Ұ��մϴ�)"+strOrder+error_msg;
		case -98: return "�˻� ����� �����ϴ�"+strOrder+error_msg;
		case -99: return "��Ʈ��ũ ���� �Դϴ�. ��Ʈ��ũ�� Ȯ���Ͽ� �ֽʽÿ�(-99)"+strOrder+error_msg;
		case -100: return "��Ʈ��ũ ���� �Դϴ�. ��Ʈ��ũ�� Ȯ���Ͽ� �ֽʽÿ�(-100)"+strOrder+error_msg;
		default: return "�˼����� �����Դϴ�."+strOrder+error_msg;
		}
	}
	
	public static String getErrorMessage(int result_code)
	{
		return getErrorMessage(result_code, 0, "");
	}
	
	public static String getErrorMessage(int result_code, int order)
	{
		return getErrorMessage(result_code, order, "");
	}
	
	public static String getErrorMessage(int result_code, String error_msg)
	{
		return getErrorMessage(result_code, 0, error_msg);
	}
	
	/* ========================================================================================== */
	
}