package com.ktdsuniversity.edu.hello_spring.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

public final class RequestUtil {

	/**
	 * 요청자의 정보를 가져온다
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes request = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return request.getRequest();
	}
	
	/**
	 * 요청자의 IP를 가져온다
	 * @return
	 */
	public static String getIp() {
		return getRequest().getRemoteAddr();
	}
}
