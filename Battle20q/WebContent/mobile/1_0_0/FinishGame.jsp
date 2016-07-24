<%@ page contentType="text/html;charset=utf-8"
	import="java.sql.*, java.util.*, java.lang.*, battle20q.*, javax.json.*"
%><jsp:useBean id="dbcon" scope="request" class="battle20q.connectDB"/><jsp:useBean id="clist" scope="request" class="battle20q.connectListbean"/><%
	request.setCharacterEncoding("utf-8");
	Hashtable listdata = null;  // 조회된 데이터용 Hashtable
	
	//서버에서 변수 받기	 
	String game_idx = cmnUtil.NVL(request.getParameter("game_idx"),""); 
	String winner_qa = cmnUtil.NVL(request.getParameter("winner_qa"),""); 
	String win_id = cmnUtil.NVL(request.getParameter("win_id"),""); 
	String lose_id = cmnUtil.NVL(request.getParameter("lose_id"),""); 

	int result_code = 0;
	String result_msg = "성공";
	String result_type = "map";
	
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
			String query = " update t_game	\n"+
						" set winner_qa='"+winner_qa+"',	finish_date=now()		\n"+
						"	where game_idx="+game_idx+"	\n" ;
				  
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
	
	if(result_code==0)
	{
		try{
			String query = " update t_user	\n"+
						" set win_count=win_count+1, lose_count=lose_count+1,	last_play_time=now()		\n"+
						"	where user_id="+win_id+"	\n" ;
				  
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
	

	if(result_code==0)
	{
		try{
			String query = " update t_user	\n"+
						" set win_count=win_count+1, lose_count=lose_count+1,	last_play_time=now()		\n"+
						"	where user_id="+lose_id+"	\n" ;
				  
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
	json_main.add("map", json_obj); //map
	
	out.print(json_main.build().toString());
	
%>
