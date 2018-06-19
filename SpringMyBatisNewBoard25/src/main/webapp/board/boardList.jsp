<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.myin.team25.domain.*" %>

<%@include file="../include/header.jsp" %>
<%
	String smemberId = "";	
    if (session.getAttribute("sMemberId") != null) {
	smemberId = (String)session.getAttribute("sMemberId");
    }	
    
	ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");	
	PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
%> 
<table border="1" width="100%" style="text-align:center;">
 <tr>
 <td><h1> 목록보기 &nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/MemberListController"> 회원정보가기</a> </h1> </td> 
 </tr>
 </table>
 <form name="searchform" action="<%=request.getContextPath()%>/BoardListController" method="post">
 <table align="right">
 <tr>
 <td>
 <select name="searchType">
 <option value="subject">제목</option>
 <option value="writer">작성자</option>
 </select>
 </td>
 <td>
 <input type="text" name="keyword" size="12" maxlength="12">
 </td>
 <td>
 <input type="submit" name="submit" value="검색" />
 </td>
 </tr>
 </table>
 </form>
 
 <table border="1" width="100%" style="text-align:center;">
 <tr style="text-align:center;background-color:green;">
 <td>글번호</td>
 <td width="50%">제목</td> 
 <td>작성자</td>
 <td>작성일</td> 
 </tr> 

<% int num =pm.getTotalCount()-((pm.getScri().getPage()-1)*pm.getScri().getPerPageNum());

for (BoardVo bvo : alist )
{ %>
	<tr>
	<td><%=num%></td>
	<td style="text-align:left;">
	&nbsp;&nbsp;	
	<% 
	for (int i=1;i<=bvo.getLeftright();i++){
		out.print("&nbsp;&nbsp;");
		if (i == bvo.getLeftright()){
			out.print("☞");
		}
	} 
	%>	
	<a href="<%=request.getContextPath() %>/BoardContentController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bvo.getBbidx()%>">
	<% 
	if (pm.getScri().getSearchType() != null){
		if (pm.getScri().getSearchType().equals("subject")){
			out.println(bvo.getSubject().replaceAll(pm.getScri().getKeyword(), "<span style='color:red;font-weight:bold'>"+pm.getScri().getKeyword()+"</span>"));
		}else{
			out.println(bvo.getSubject());
		}
	}else{
		out.println(bvo.getSubject());
	}
	%>		
	</a>
	</td>	
	<td>
	<% 
	if (pm.getScri().getSearchType() != null){
		if (pm.getScri().getSearchType().equals("writer")){
			out.println(bvo.getWriter().replaceAll(pm.getScri().getKeyword(), "<span style='color:red;font-weight:bold'>"+pm.getScri().getKeyword()+"</span>"));
		}else{
			out.println(bvo.getWriter());
		}
	}else{
		out.println(bvo.getWriter());
	}
	%>	
	</td>
	
	<td><%=bvo.getWriteday() %></td>		
	</tr>	
<% 
	num = num -1;
	} 
%>
 </table> 
 
 <div style="text-align: center;">
	 <div class="jb-center">
		 <ul class="pagination">
		 	<li>
		 	<%if (pm.isPrev() == true) { %>
	 			<a href="<%=request.getContextPath() %>/BoardListController<%=pm.makeSearch(pm.getStartPage()-1)%>"> 			
		 			<span class="glyphicon glyphicon-chevron-left"></span>
	 			</a> 		
 			<% } %> 			
			</li>
 	 	
 	 	  <% for(int i= pm.getStartPage(); i<=pm.getEndPage(); i++) { %>
           <li <% if (pm.getScri().getPage() == i){ %>class="active" <% } %>>     
           		<a href="<%=request.getContextPath() %>/BoardListController<%=pm.makeSearch(i)%>"><%=i %></a>
           </li>              
          <% } %>
  
		 	<li>
		 	<% if (pm.isNext() && pm.getEndPage() >0) { %>
 			<a href="<%=request.getContextPath() %>/BoardListController<%=pm.makeSearch(pm.getEndPage()+1)%>">
	 			<span class="glyphicon glyphicon-chevron-right"></span>
 			</a>
 			<% } %>
			</li>
		 </ul>
	 </div>
 </div> 
 
 <center>
 <h2>
 <a href="<%=request.getContextPath()%>/BoardWriteController<%=pm.makeSearch(pm.getScri().getPage())%>"> 글쓰기</a> 
 </h2>
 </center>

<%@include file="../include/footer.jsp" %>












