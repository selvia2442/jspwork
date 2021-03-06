<%@ page contentType="text/html;charset=utf-8"
	import="java.sql.*, java.util.*, java.lang.*, battle20q.*, javax.json.*"
%><jsp:useBean id="dbcon" scope="request" class="battle20q.connectDB"/><jsp:useBean id="clist" scope="request" class="battle20q.connectListbean"/><%
	request.setCharacterEncoding("utf-8");
	Hashtable listdata = null;  // 조회된 데이터용 Hashtable
	
	//서버에서 변수 받기	 
	String title = cmnUtil.NVL(request.getParameter("title"),"");  
	String user_id = cmnUtil.NVL(request.getParameter("user_id"),"");  
	

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
			String query = " insert into t_room	\n" +
					"(title, player1_id, create_date) values"+
					" ('"+title+"', '"+user_id+"', now())";
				  
			PreparedStatement pStmt = clist.setPStmt(conn, query, Statement.RETURN_GENERATED_KEYS);
			if (pStmt == null) {
				result_code = -99;
				result_msg = cmnUtil.getErrorMessage(result_code, 2, clist.getErrorMessage());		
			}
			else{
				int ret = pStmt.executeUpdate();
				if(ret!=-1){
					try{
						ResultSet tmp_rs = pStmt.getGeneratedKeys();
						if (tmp_rs.next()) {
							int no = tmp_rs.getInt(1);
							json_obj.add("room_no", Integer.toString(no));
							json_obj.add("title", title);
						} else {
						    // do what you have to do
						}

					}catch(Exception ex){
						result_code = -99;
						result_msg = cmnUtil.getErrorMessage(result_code, 1, ex.getMessage());
					}
				}
				else{
					result_code = -99;
					result_msg = cmnUtil.getErrorMessage(result_code, 3, "방 생성에 실패하였습니다.");
				}
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
