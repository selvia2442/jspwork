<%@ page contentType="text/html;charset=utf-8"
	import="java.sql.*, java.util.*, java.lang.*, battle20q.*, javax.json.*"
%><jsp:useBean id="dbcon" scope="request" class="battle20q.connectDB"/><jsp:useBean id="clist" scope="request" class="battle20q.connectListbean"/><%
	request.setCharacterEncoding("utf-8");
	Hashtable listdata = null;  // 조회된 데이터용 Hashtable
	
	//서버에서 변수 받기	 
	String user_id = cmnUtil.NVL(request.getParameter("user_id"),""); 
	String room_no = cmnUtil.NVL(request.getParameter("room_no"),""); 

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
			String query = " update t_room	\n"+
					"	set player2_id='"+user_id+"',	people_cnt=2	\n"+
					"	where room_no="+room_no+"	\n" ;
				  
			PreparedStatement pStmt = clist.setPStmt(conn, query);
			if (pStmt == null) {
				result_code = -99;
				result_msg = cmnUtil.getErrorMessage(result_code, 4, clist.getErrorMessage());		
			}
			else{
				pStmt.executeUpdate();
				pStmt.close();
			}
		}catch(Exception ex){
			result_code = -99;
			result_msg = cmnUtil.getErrorMessage(result_code, 3, ex.getMessage());
		}
	}
	
	if(result_code==0)
	{
		String query = 
				" select 						\n" +
				" user_id						\n" +
				" , user_name						\n" +
				" , user_img						\n" +
				"   FROM t_user						\n" +
				" where user_id = (select player1_id FROM t_room where room_no="+room_no+")		\n" ;

		listdata = clist.getAllcnt(conn, query); // (Connection, 쿼리문), 전체 데이터 카운터겸용
		int totalrow = clist.getTotalrows();         // 전체 레코드수
		if(clist.isError())
		{
			result_code = -99;
			result_msg = cmnUtil.getErrorMessage(result_code, clist.getErrorMessage());
		}
		else{
			//MAP
			if(totalrow!=0)
			{	
				json_obj.add("user_id", listdata.get(new Integer(0)).toString());
				json_obj.add("user_name", listdata.get(new Integer(1)).toString());
				json_obj.add("user_img", listdata.get(new Integer(2)).toString());
			}
			else{
				result_code = -1; //아이디가 없거나 패스워드가 틀립니다.
				result_msg = cmnUtil.getErrorMessage(result_code);
			}
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
