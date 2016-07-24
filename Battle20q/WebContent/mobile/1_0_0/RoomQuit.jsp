<%@ page contentType="text/html;charset=utf-8"
	import="java.sql.*, java.util.*, java.lang.*, battle20q.*, javax.json.*"
%><jsp:useBean id="dbcon" scope="request" class="battle20q.connectDB"/><jsp:useBean id="clist" scope="request" class="battle20q.connectListbean"/><%
	request.setCharacterEncoding("utf-8");
	Hashtable listdata = null;  // 조회된 데이터용 Hashtable
	
	//서버에서 변수 받기	 
	String user_id = cmnUtil.NVL(request.getParameter("user_id"),""); 
	String master_flag = cmnUtil.NVL(request.getParameter("master_flag"),""); 
	String room_no = cmnUtil.NVL(request.getParameter("room_no"),""); 

	int result_code = 0;
	String result_msg = "성공";
	String result_type = "resultonly";
	
	Map<String, String> m = new HashMap<String, String>();
	JsonBuilderFactory factory = Json.createBuilderFactory(m);
	JsonObjectBuilder json_main = factory.createObjectBuilder();
	JsonObjectBuilder json_obj = factory.createObjectBuilder(); //MAP
	

	Connection conn = dbcon.getConnection(); 
	if (conn == null) {   
		result_code = -100;
		result_msg = cmnUtil.getErrorMessage(result_code);
	}
	
	if(result_code==0)
	{
		try{
			String query;
			if(master_flag.equals("y")) {
				query = " update t_room	\n"+
						"	set state='f'		\n"+
						"	, finish_date=now()		\n"+
						"	where room_no="+room_no+"	\n" ;
			} else {
				query = " update t_room	\n"+
						"	set player2_id='', 	people_cnt=1	\n"+
						"	where room_no="+room_no+"	\n" ;
			}
				  
			PreparedStatement pStmt = clist.setPStmt(conn, query);
			if (pStmt == null) {
				result_code = -99;
				result_msg = cmnUtil.getErrorMessage(result_code, 2, clist.getErrorMessage());		
			}
			else{
				pStmt.executeUpdate();
				pStmt.close();
			}
		}catch(Exception ex){
			result_code = -99;
			result_msg = cmnUtil.getErrorMessage(result_code, 2, ex.getMessage());
		}
	}
	
	if(result_code!=-100)
	{
		dbcon.conClose(conn);	
	}
	
	json_main.add("resulttype", result_type);
	json_main.add("result", Integer.toString(result_code));
	json_main.add("msg", result_msg);

	out.print(json_main.build().toString());
	
%>
