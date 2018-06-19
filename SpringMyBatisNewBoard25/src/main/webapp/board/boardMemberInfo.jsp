<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.myin.team25.domain.*" %>
<%@ page import="java.util.HashMap" %>

<%@include file="../include/header.jsp" %>
<%
	String smemberId = "";	
    if (session.getAttribute("sMemberId") != null) {
	smemberId = (String)session.getAttribute("sMemberId");
    }	
    
	ArrayList<HashMap<String,Object>> alist = (ArrayList<HashMap<String,Object>>)request.getAttribute("alist");	
//	PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
%> 
<table border="1" width="100%" style="text-align:center;">
 <tr>
 <td><h1>게시물 작성자 회원정보 보기</h1> </td> 
 </tr>
 </table>
 
 <table border="1" width="100%" style="text-align:center;">
 <tr style="text-align:center;background-color:green;">
 <td>글번호</td>
 <td width="50%">게시글번호</td> 
 <td>회원이름</td>
 <td>나이</td>
 <td>ip</td> 
 </tr> 

<% int num =1;

for (HashMap<String,Object> map : alist )
{ %>
	<tr>
	<td><%=num%></td>
	<td style="text-align:left;">
	<%=map.get("BBIDX") %>	
	</td>	
	<td>
	<%=map.get("MEMBERNAME") %>	
	</td>	
	<td><%=map.get("AGE") %></td>
	
	<td><%=map.get("IP") %></td> 		
	</tr>	
<% 
	num = num +1;
	} 
%>
 </table>  

<%@include file="../include/footer.jsp" %>












