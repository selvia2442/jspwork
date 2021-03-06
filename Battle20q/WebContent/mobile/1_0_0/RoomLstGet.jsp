<%@ page contentType="text/html;charset=utf-8"
	import="java.sql.*, java.util.*, java.lang.*, battle20q.*, javax.json.*"
%><jsp:useBean id="dbcon" scope="request" class="battle20q.connectDB"/><jsp:useBean id="clist" scope="request" class="battle20q.connectListbean"/><%
	request.setCharacterEncoding("utf-8");
	Hashtable listdata = null;  // 조회된 데이터용 Hashtable
	
	int result_code = 0;
	String result_msg = "성공";
	String result_type = "list";
	int cnt = 0;
	
	Map<String, String> m = new HashMap<String, String>();
	JsonBuilderFactory factory = Json.createBuilderFactory(m);
	JsonObjectBuilder json_main = factory.createObjectBuilder();
	JsonArrayBuilder json_list = factory.createArrayBuilder(); //list
	

	Connection conn = dbcon.getConnection(); 
	if (conn == null) {   
		result_code = -100;
		result_msg = cmnUtil.getErrorMessage(result_code);
		return;		
	}
	
	if(result_code==0)
	{
		String query = 
					" select room_no " +
					", title	\n" +
					", people_cnt	\n" +
					", state	\n" +
					", DATE_FORMAT(create_date, '%H:%i')	\n" +
					"   FROM t_room			\n" +
					" where state='o' and people_cnt=1	\n" +
					" order by create_date		\n";
	
		listdata = clist.getAllcnt(conn, query); // (Connection, 쿼리문), 전체 데이터 카운터겸용
		int totalrow = clist.getTotalrows();         // 전체 레코드수
		cnt = totalrow;
		if(clist.isError())
		{
			result_code = -99;
			result_msg = cmnUtil.getErrorMessage(result_code, clist.getErrorMessage());
		}
		else{
			//LIST
			int field_cnt = 5;
			for(int i = 0; i<totalrow; i++)
			{
				int idx = 0;
				
				JsonObjectBuilder obj = factory.createObjectBuilder();
				obj.add("room_no", listdata.get(new Integer(i*field_cnt+(idx++))).toString());
				obj.add("title", listdata.get(new Integer(i*field_cnt+(idx++))).toString());
				obj.add("people_cnt", listdata.get(new Integer(i*field_cnt+(idx++))).toString());
				obj.add("state", listdata.get(new Integer(i*field_cnt+(idx++))).toString());
				obj.add("create_date", listdata.get(new Integer(i*field_cnt+(idx++))).toString());
				json_list.add(obj);
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
	json_main.add("cnt", Integer.toString(cnt)); //list
	json_main.add("list", json_list); //list
	
	out.print(json_main.build().toString());
	
%>
