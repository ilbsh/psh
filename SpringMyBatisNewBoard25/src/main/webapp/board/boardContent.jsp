<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.*" %>
<%@ include file="../include/header.jsp" %>
<% 
BoardVo bv  = (BoardVo)request.getAttribute("bv"); 
PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
int sMemberMidx = (int)session.getAttribute("sMemberMidx");
%>
<!--  jquery 사용하기위한  jquery cdn주소는 header.jsp에 들어있음 -->
<script type="text/javascript">
$(function(){	
	
		$.boardCommentList();
		
		$('#more').click(function(){
		
			var block = $("#nextBlock").val();			
			//alert(block);
			
			$.ajax({
				type : "GET",
				url  : "/comments/"+block+"/<%=bv.getBbidx()%>",
				datatype : "text",
				cache : false,
				error : function(){				
					alert("error");
				},
				success : function(data){
					//alist와 nextBlock나온다
					
					$("#nextBlock").val(data.nextBlock);
					
					commentList(data.alist);
					}			
			});	 
			
		});
		 
		 $('#save').click(function(){			
				
			 var bbidx = $("#bbidx").val();	
			 var cwriter = $("#cwriter").val();
			 var ccontent = $("#ccontent").val();			 
			 
			 $.ajax({
					type : "POST",
					url  : "/comments",
					headers : {
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "POST"
					},
					datatype : "text",
					data : JSON.stringify({						
						bbidx : bbidx,
						cwriter : cwriter,
						ccontent : ccontent
					}),
					cache : false,
					error : function(){				
						alert("error");
					},
					success : function(data){
					
							if (data == null){
								alert("데이타없음");
							}
							 
							alert(data);
							
							$.boardCommentList();
							
							$("#bbidx").val("");
							$("#cwriter").val("");
							$("#ccontent").val("")
							}			
				});	
		});			 
});

$.boardCommentList = function(){
	
	$.ajax({
		type : "GET",
		url  : "/comments/all/<%=bv.getBbidx()%>",
		datatype : "text",
		cache : false,
		error : function(){				
			alert("error");
		},
		success : function(data){			
			commentList(data);
			}			
	});	 	
}

$.del = function(cidx){
	
	var cidx = cidx;		
	var bbidx = <%=bv.getBbidx()%>;
	
	 $.ajax({
			type : "GET",
			url  : "/comments/"+cidx+"/"+bbidx,		
			datatype : "text",				
			cache : false,
			error : function(){				
				alert("error");
			},
			success : function(data){			
				
				if (data == null){
					alert("데이타없음");
				}
				 
				alert(data);
				
				
				$.boardCommentList();
			}			
			});	
}	

function commentList(data){
	var str = '';						
	var sMemberMidx = <%=sMemberMidx%>;
	
	$(data).each(function(){
		
		var delinput = "";
		if (sMemberMidx == this.memberMidx) {
			delinput ="<li class='sub4'><button class='btn btn-danger' onclick='$.del("+this.cidx+")'>삭제</button></li>";
			}
		
		str += "<ul><li class='sub1'>"+this.cidx   + "</li>" 
			+  "<li class='sub2'>"+this.cwriter + "</li>"
			+  "<li class='sub3'>"+this.ccontent  + "</li>"
			+  delinput
			+  "</ul>";					
	});
	
	$('#tbl').html("<ul><li class='title1'>번호</li>"
					+"<li class='title2'>작성자</li>"
					+"<li class='title3'>내용</li>"
					+"<li class='title4'>삭제여부</li>"
					+"</ul>"
					+str+"");				

}
</script>

<div class="box-body">
<h1>내용보기</h1>
<br>
	<div class="form-group">
	<label for="examInputEmail1">제목 </label>
	<input type="text" class="form-control" value="<%=bv.getSubject() %>" readonly="readonly" />
	</div>
	
	<div class="form-group">
	<label for="examInputPassword1">내용 </label>
	<textarea class="form-control" rows="3" readonly="readonly">
	<%=bv.getContent() %>
	</textarea>	  
	</div>
	
	<div class="form-group">
	<label for="examInputEmail1">작성자 </label>
	<input type="text" class="form-control" value="<%=bv.getWriter() %>" readonly="readonly" />
	</div>
	
	<div class="form-group">
	<label for="examInputEmail1">작성일 </label>
	<input type="text" class="form-control" value="<%=bv.getWriteday() %>" readonly="readonly" />
	</div>
</div>

<div class="box-footer">
	<div class="form-group" style="float:right;">
		<label for="examInputEmail1">
		<a href="<%=request.getContextPath()%>/BoardModifyController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bv.getBbidx()%>">수정</a>&nbsp;
		<a href="<%=request.getContextPath()%>/BoardDeleteController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bv.getBbidx()%>">삭제</a>&nbsp; 
		<a href="<%=request.getContextPath()%>/BoardReplyController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bv.getBbidx()%>&oidx=<%=bv.getOidx()%>&updown=<%=bv.getUpdown()%>&leftright=<%=bv.getLeftright()%>">답변</a>&nbsp; 
		<a href="<%=request.getContextPath()%>/BoardListController<%=pm.makeSearch(pm.getScri().getPage())%>">목록</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</label>
	</div>
</div>
<div id="commenttbl" class="box-body">
	<div class="form-group">
	<label for="examInputEmail1">작성자</label>
	<input type="text" class="form-control" name="cwriter" id ="cwriter" placeholder="이름입력하세요" />
	<input type="hidden" name="bbidx" id="bbidx"  value="<%=bv.getBbidx()%>" />	
	<label for="examInputEmail1">내용</label>
	<textarea class="form-control" name="ccontent" id="ccontent" rows="3" placeholder="내용입력하세요"></textarea>
	</div>
	
	<div class="form-group">
	<input type="button" name="save" id="save" value="저장" class="comment_input" />	
	</div>
</div>

<div id="tbl"></div>
&nbsp;
<div class="form-group">	
<center>
<button id='more' class='btn btn-primary'>더보기</button>
</center>	
<input id='nextBlock' type='hidden' value='2' />
</div>

<%@ include file="../include/footer.jsp" %>