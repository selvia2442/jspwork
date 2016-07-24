<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="com.oreilly.servlet.MultipartRequest,com.oreilly.servlet.multipart.DefaultFileRenamePolicy,java.util.*,java.io.*, javax.json.*,battle20q.*" %><%@ page import="java.sql.*" %><%
//서버용으로 여기서 테스트 할수 없음	
	request.setCharacterEncoding("UTF-8");

	int result_code = 0;
	String result_msg = "성공";
	String result_type="map";

	Map<String, String> m = new HashMap<String, String>();
	JsonBuilderFactory factory = Json.createBuilderFactory(m);
	JsonObjectBuilder json_main = factory.createObjectBuilder();
	JsonObjectBuilder json_obj = factory.createObjectBuilder();	

	String realFolder = "";
	String upimage = "";
	int maxSize = 1024*1024*5;
	String encType = "utf-8";
	String savefile = "";
	
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
	String today = formatter.format(new java.util.Date());
 
	String subdir_y=today.substring(0,4);
	String subdir_md=today.substring(4,8);
	
	String savePath = "/home/www/"+subdir_y+"/"+subdir_md+"/";  //경로	  
	File targetDir = new File(savePath);
	if(!targetDir.exists()) {
		targetDir.mkdirs();
	}	
	savefile = "/"+subdir_y+"/"+subdir_md; 


	realFolder = savePath;	
	try{
		MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
		Enumeration<?> files = multi.getFileNames();
	    String file1 = (String)files.nextElement();
	    upimage = multi.getFilesystemName(file1);
	    json_obj.add("uppath",savefile+"/"+upimage);
	} catch(Exception e) {
		result_code = -97;
		result_msg = cmnUtil.getErrorMessage(result_code, e.getMessage());
	}
	
	
	json_main.add("resulttype", result_type);
	json_main.add("result", Integer.toString(result_code));
	json_main.add("msg", result_msg);
	json_main.add("map", json_obj);
	
	out.print(json_main.build().toString());
%>
