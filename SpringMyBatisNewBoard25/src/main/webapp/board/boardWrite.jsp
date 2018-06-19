<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.*" %>
<% PageMaker pm = (PageMaker)request.getAttribute("pageMaker");%>

<%@ include file="../include/header.jsp" %>


<style>
.fileDrop {
width: 100%;
height:200px;
border:1px dotted blue;
}

small {
margin-left:3px;
font-weight:bold;
color:gray;
}
</style>
<script type="text/javascript">
function check() {	
  
  var formname = document.frm;
  
  if (formname.subject.value =="") {
	  alert("제목입력하세요");
	  formname.subject.focus();	  
	  return ;	  
  } else if (formname.content.value ==""){	
	  alert("내용 입력하세요");
	  formname.content.focus();	  
	  return ;
  } else if (formname.writer.value ==""){	
	  alert("작성자 입력하세요");
	  formname.writer.focus();	  
	  return ;
  } else if (formname.password.value ==""){	  
	  alert("비밀번호입력하세요");
	  formname.password.focus();	  
	  return ;
  } 
    var res;
  	res = confirm("등록하시겠습니까?");
  
  	if (res == true) {
	   	formname.method ="post";
	   	formname.action ="<%=request.getContextPath() %>/BoardWriteActionController";
	   	formname.submit();  
  	}
  	return ;
}	

function addFilePath(msg){
	alert(msg);	
}

function checkImageType(fileName){
	
	var pattern = /jpg$|gif$|png$|jpeg$/i;
	alert(fileName.match(pattern));
	
	return fileName.match(pattern);
}

function getOriginalName(fileName){
	alert(fileName);
	//이미지파일이면 원본이름 안쓴다
	if (checkImageType(fileName)) {
		return;
	}
	
	var idx = fileName.lastindexOf("_")+1;
	
	alert(idx);
	return fileName.substr(idx);
}

//파일이 이미지일경우
function getImageLink(fileName){
	if (!checkImageType(fileName)) {
		return;
	}
	//위치 폴더뽑기
	var front = fileName.substr(0,12);
	//파일이름뽑기
	//_는 빼고
	var end = fileName.substr(14);
	
	return front+end;	
}

$(document).ready(function(){
	
	$(".fileDrop").on("dragenter dragover",function(event){
	
		event.preventDefault();		
	});
	
	$(".fileDrop").on("drop",function(event){
		
		event.preventDefault();
		
		var files = event.originalEvent.dataTransfer.files;
		var file = files[0];
		
		var formData = new FormData();
		
		formData.append("file",file);
		
		$.ajax({
			url:'/uploadAjax',
			data: formData,
			dataType:'text',
			processData:false,
			contentType:false,
			type:'POST',
			success : function(data){
				alert(data);
				
				var str ="";
				$("#uploadfile").val(data.substr(14));
				
				if(checkImageType(data)){
					str ="<div>"
					+ "<a href='displayFile?fileName="+getImageLink(data)+"'>"
					+ "<img src='displayFile?fileName="+data+"' />"
					+ getImageLink(data) 
					+ "</a>"
					+ "</div>";
				}else{
					str = "<div>"
						+ "<a href='displayFile?fileName="+data+"'>"
						+ getOriginalName(data) 
						+ "</a>"
						+ "</div>";
				}
				
				$(".uploadedList").append(str);
			}		
			
		});	
		
	});
	
	
});

</script>
</head>
<body>
	<form name="frm" enctype="multipart/form-data" target="team25">
		<input type=hidden name="page" value="<%=pm.getScri().getPage()%>" />
		<input type=hidden name="searchType"
			value="<%=pm.getScri().getSearchType()%>" /> <input type=hidden
			name="keyword" value="<%=pm.getScri().getKeyword()%>" />

		<table border=1 width="100%" height="600px">
			<tr>
				<td>제목</td>
				<td><input type="text" name="subject" id="subject" size="20"
					maxlength="20" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" rows=20 cols=100></textarea></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="writer" size="20" /></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password" size="10"
					maxlength="10" /></td>
			<tr>
				<td>파일업로드</td>
				<td>
 <input type="hidden" name="uploadfile" id= "uploadfile"> 
</td>
			</tr>

			<tr>
				<td></td>
				<td align="center"><input type="button" name="button"
					value="확인" onclick="javascript:check();" /> <input type="button"
					value="리셋" onclick="reset();" /> <input type="button"
					value="목록"
					onclick="javascript:document.location.href='<%=request.getContextPath()%>/BoardListController';" />
				</td>
			</tr>
		</table>
	</form>
	<div class="fileDrop"></div>
<div class="uploadedList"></div>
	

	<!-- <iframe name="team25"></iframe>

	<script>

 function addFilePath(msg){
	alert(msg);
}

</script> -->
<%@ include file="../include/footer.jsp" %>