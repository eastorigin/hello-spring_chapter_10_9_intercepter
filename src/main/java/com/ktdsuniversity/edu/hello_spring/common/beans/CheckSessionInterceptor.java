package com.ktdsuniversity.edu.hello_spring.common.beans;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ktdsuniversity.edu.hello_spring.member.vo.MemberVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * 요청과 응답을 가로채는 intercepter
 */
public class CheckSessionInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler) // 원래 동작을 시켜야하는 controller가 handler에 들어있음
			throws Exception {
		
		/*
		 * controller가 실행되기 전에 로그인 session을 검사해서 로그인이 되어있지 않다면 로그인이 페이지를 보여주도록 한다
		 */
		
		// 1. session 가져오기
		HttpSession session = request.getSession();
		
		// 2. session 존재한다면 controller 실행시키기
		MemberVO memberVO = (MemberVO) session.getAttribute("_LOGIN_USER");
		if(memberVO != null) {
			// 로그인을 했다
			return true; // controller를 계속해서 실행한다
		}
		
		// 3. session 존재하지 않는다면 로그인 페이지 보여주기
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/member/memberlogin.jsp");
		rd.forward(request, response);
		
		return false; // controller를 실행시키지 않는다
	}

//	@Override
//	public void postHandle(HttpServletRequest request, 
//						   HttpServletResponse response, 
//						   Object handler, // 실행됐던 controller
//						   ModelAndView modelAndView) throws Exception { // controller 실행 된 후라 controller가 반환해준 modelAndView가 있다
//		System.out.println("CheckSessionIntercepter.postHandle :" + handler);
//		System.out.println("CheckSessionIntercepter.postHandle :" + modelAndView);
//	}
//	
//	@Override
//	public void afterCompletion(HttpServletRequest request, 
//								HttpServletResponse response, 
//								Object handler, // 실행됐던 controller
//								Exception ex) // 여전히 catch가 되지 않는
//			throws Exception {
//		System.out.println("CheckSessionIntercepter.afterCompletion: " + handler);
//	}
}
