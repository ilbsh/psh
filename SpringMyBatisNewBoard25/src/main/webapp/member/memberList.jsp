<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.MemberVo"  %>
<%@ page import="java.util.ArrayList" %>

<%@include file="../include/header.jsp" %>

<script>
function Modify(){	
	document.location.href='<%=request.getContextPath()%>/MemberModifyController';	
	return;
}
</script>

<%
	String smemberId = null;	
    if (session.getAttribute("sMemberId") != null) {
	smemberId = (String)session.getAttribute("sMemberId");
    }
    
	
	if (smemberId == null){
	out.println("로그인 하세요");
	out.println("<a href ='"+request.getContextPath()+"/MemberJoinController'>회원가입하기</a>");
	}else{
	out.println(smemberId+"님 안녕하세요");
	out.println("<a href ='"+request.getContextPath()+"/MemberLogoutController'>로그아웃</a>"+
				"<input type='button' name='button' value='회원정보수정' onclick='Modify();' />");
	}
	
	ArrayList<MemberVo> alist =  (ArrayList<MemberVo>)request.getAttribute("alist");	
%> 
<h1>회원정보 리스트 &nbsp;&nbsp; 
<% if (smemberId != null){ %>
<a href="<%=request.getContextPath()%>/BoardListController">글목록</a>
<% } %>
 </h1>
 <table border="1" width="100%" style="text-align:center;">
 <tr>
 <td>회원번호</td>
 <td>아이디</td>
 <td>비밀번호</td>
 <td>이름</td>
 <td>성별</td>
 <td>주민번호</td>
 <td>주소</td>
 <td>이메일</td>
 <td>IP</td>
 <td>부서</td>
 <td>가입일</td>
 <td>수정일</td>
 </tr> 

<% for (MemberVo mvo : alist ) { %>
<tr>
<td><%=mvo.getMemberMidx() %></td>
<td><%=mvo.getMemberId() %></td>
<td><%=mvo.getMemberPassword() %></td>
<td><%=mvo.getMemberName() %></td>
<td><%=mvo.getMemberSex() %></td>
<td><%=mvo.getMemberJumin() %></td>
<td><%=mvo.getMemberAddr() %></td>
<td><%=mvo.getMemberEmail() %></td>
<td><%=mvo.getMemberIp() %></td>
<td><%=mvo.getBidx() %></td>
<td><%=mvo.getWriteday() %></td>
<td><%=mvo.getModifyday() %></td>			
</tr>	
 <% } %>
 </table>
 
 <div style="text-align: center;">
	 <div class="jb-center">
		 <ul class="pagination">
		 	<li>
 			<span class="glyphicon glyphicon-chevron-left">
 			</span> 		
			</li>
 	 	
 	 	  <% for(int i=1; i<=10;i++) { %>
           <li>     
           <a href="#"><%=i %></a>
           </li>              
          <% } %>
  
		 	<li>
 			<span class="glyphicon glyphicon-chevron-right">
 			</span>
			</li>
		 </ul>
	 </div>
 </div>
 
 <%@include file="../include/footer.jsp" %>
