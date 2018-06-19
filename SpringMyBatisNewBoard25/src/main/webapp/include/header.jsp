<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>team25</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<link href="/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/dist/css/AdminLTE.min.css" rel="stylesheet"
	type="text/css" />
<link href="/resources/dist/css/skins/_all-skins.min.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/css/comment.css" rel="stylesheet" type="text/css" />

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
.morebtn {
	float: center;
	text-align: center;
}
</style>
</head>

<!-- jQuery 3.3.1 -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

<body class="skin-blue sidebar-mini">
	<!-- wrapper는 footer.jsp에서  종료처리함 -->
	<div class="wrapper">

		<header class="main-header">
			<!-- Logo -->
			<a href="/" class="logo"> <span class="logo-mini"></span> <span
				class="logo-lg"><b>TEAM25</b> PROJECT</span>
			</a>

			<nav class="navbar navbar-static-top" role="navigation"></nav>
		</header>

		<aside class="main-sidebar">
			<section class="sidebar">
				<div class="user-panel">
					<div class="pull-left image">
						<img src="/resources/dist/img/로고.jpg" class="img-circle" alt="로고" />
					</div>
				</div>

				<ul class="sidebar-menu">
					<li class="header">MAIN NAVIGATION</li>
					<li class="treeview"><a href="#"> <i
							class="fa fa-dashboard"></i> <span>메뉴</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a
								href="<%=request.getContextPath()%>/MemberListController"><i
									class="fa fa-circle-o"></i> 회원정보보기</a></li>
							<li><a
								href="<%=request.getContextPath()%>/MemberLoginController"><i
									class="fa fa-circle-o"></i> 로그인하고 글쓰기</a></li>
						</ul></li>

					<li class="treeview"><a href="#"> <i
							class="fa fa-dashboard"></i> <span>예제</span> <i
							class="fa fa-angle-left pull-right"></i>
					</a>
						<ul class="treeview-menu">
							<li><a href="/BoardMemberInfoController"><i
									class="fa fa-circle-o"></i> 게시물작성회원정보보기</a></li>
							<li><a href="/BoardListAjaxController"><i
									class="fa fa-circle-o"></i> AJAX로 게시판사용</a></li>
						</ul></li>

				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content 화면 -->
		<div class="content-wrapper">