package com.smartweb.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminCheckInterceptor extends HandlerInterceptorAdapter{
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*
		HttpSession session = request.getSession();
		String login_yn = (String)session.getAttribute("login_yn");
		
		if(!login_yn.equals("Y")){
			response.sendRedirect("/login/login.do");
			return false;
		}
		*/
				
		return true;
	}

}
