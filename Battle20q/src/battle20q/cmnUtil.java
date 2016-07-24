package battle20q;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class cmnUtil {
/* ========================================================================================== */
	
	/*
	 *  문자열 변경
	 *  (str1 : 원본 문자열, str2 : 바꾸어야 될 문자열, str3 : 바꿀 문자열)
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
	 *  문자열 있는지 확인
	 *  (str1 : 원본 문자열, str2 : 확인할 문자열)
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
	 *  NULL값을 빈값이나 지정된 값으로 변경
	 */
	public static String NVL(String str, String nvlstr) {
    	if(str == null || str.trim().equals("") || str.trim().equals("null"))
    		return nvlstr;
    	else
    		return str;
	}
	
	/*
	 *  NULL값을 빈값이나 지정된 값으로 변경
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
	 *  NULL값을 빈값이나 지정된 값으로 변경 (Integer형)
	 */
	public static int NVL(String str, int nvlint) {
		if(str == null || str.trim().equals("") || str.trim().equals("null"))
			return nvlint;
		else
			return Integer.parseInt(str);
	}
	
	/*
	 *  NULL값을 빈값이나 지정된 값으로 변경 (Integer형)
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
	 *  문자열에서 특정문자 제거
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
	 *  System Error발생시 ', " char제외
	 */
	public static String removeChars(String str) {
		
		String tmp_str = str;
		tmp_str = removeChars(tmp_str, '\'');
		tmp_str = removeChars(tmp_str, '"');
		return tmp_str;
	} 

	/*
	 *  Number format으로 변경
	 */
	public static String numberformat(String str) {
		try {
			
			return NumberFormat.getInstance().format(Long.parseLong(str));
		} catch (Exception e) {
			return str;
		}
	}

   /* 
	* 스트링 자르기 
	* 지정한 정수의 개수 만큼 빈칸(" ")을 스트링을 구한다. 
	* 절단된 String의 바이트 수가 자를 바이트 개수를 넘지 않도록 한다. 
	* 
	* @param str 원본 String 
	* @param int 자를 바이트 개수 
	* @param char + or -  (한글이 2bytes이므로 남는 1바이트처리를 위해)
	* @param tail 길이가 긴 str에 대해 뒤에 붙일 꼬리글 
	* @return String 절단된 String 
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
	 *  화면상의 공통 메세지 출력을 위해서 스크립트 Alert창 표출
	 */
	public static void msgAlert(PrintWriter out, String msg) {
		out.println("<script> alert('" + msg + "'); </script>");
	}

	/*
	 *  이전 페이지로 복귀
	 */
	public static void historyBack(PrintWriter out)	{
		out.println("<html><head><script> history.back() ; </script></head><body></body></html>");
	}
	
	/*
	 *  처리 후 원하는 페이지로 이동
	 */
	public static void loadPage(PrintWriter out, String page) {
		String script = "<html><head><script>location = \"" + page + "\";</script></head><body></body></html>";
		out.println(script);
	}

	/*
	 *  처리 후 원하는 페이지로 이동
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
		case 0: return "성송";
		case -1: return "들어갈 수 없는 방입니다.";

		case -7: return "로그인이 실패하였습니다.";
		
		case -97: return "파일업로드에 실패하였습니다(5M 이상의 파일은 업로드 불가합니다)"+strOrder+error_msg;
		case -98: return "검색 결과가 없습니다"+strOrder+error_msg;
		case -99: return "네트워크 오류 입니다. 네트워크를 확인하여 주십시요(-99)"+strOrder+error_msg;
		case -100: return "네트워크 오류 입니다. 네트워크를 확인하여 주십시요(-100)"+strOrder+error_msg;
		default: return "알수없는 오류입니다."+strOrder+error_msg;
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
