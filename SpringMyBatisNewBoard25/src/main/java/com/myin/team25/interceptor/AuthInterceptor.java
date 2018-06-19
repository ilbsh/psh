package com.myin.team25.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {
		
	 @Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
			
		 HttpSession session = request.getSession();
		 
		 //로그인 하지 않은 경우
		 if(session.getAttribute("memberVo") == null){
			
			 //이동 할 주소를 담고
			 saveDest(request);
			 response.sendRedirect(request.getContextPath()+"/spring/memberLogin");
			 return false;
		 }
		 return true;		
	 	}
	
	 private void saveDest(HttpServletRequest req)
	 {
	
		 StringBuffer url = req.getRequestURL();		 
		 String query = req.getQueryString();
		 
		
		 
		 if (query ==null || query.equals("null")){
			 query = "";
		 }else {
			 query = "?" + query;
		 }
		 
		 if (req.getMethod().equals("GET")){
			 req.getSession().setAttribute("dest", url+query);
		//	 System.out.println("url:"+url+query);
		 }		 
	 }	
}
