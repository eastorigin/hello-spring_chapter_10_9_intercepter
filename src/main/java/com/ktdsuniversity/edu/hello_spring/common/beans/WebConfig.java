package com.ktdsuniversity.edu.hello_spring.common.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ktdsuniversity.edu.hello_spring.access.dao.AccessLogDao;

// application.yml에서 설정하지 못하는 디테일한 설정을 위한 annotation
// String Bean을 수동으로 생성하는 기능
@Configuration
// Spring WebMVC에 필요한 다양한 요소를 활성화시키는 annotation
// 	- Spring Validator
//	- Spring Inteceptor
//	- ...
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private AccessLogDao accessLogDao;
	
	@Value("${app.interceptors.check-session.path-patterns}")
	private List<String> checkSessionPathPatterns;
	@Value("${app.interceptors.check-session.exclude-path-patterns}")
	private List<String> checkSessionExcludePathPatterns;
	
	@Value("${app.interceptors.check-dup-login.path-patterns}")
	private List<String> checkDupLoginPathPatterns;
	@Value("${app.interceptors.check-dup-login.exclude-path-patterns}")
	private List<String> checkDupLoginExcludePathPatterns;
	
	@Value("${app.interceptors.add-access-log.path-patterns}")
	private List<String> addAccessLogPathPatterns;
	@Value("${app.interceptors.add-access-log.exclude-path-patterns}")
	private List<String> addAccessLogExcludePathPatterns;

	/**
	 * Auto DI: @Component
	 * Manual DI: @Bean
	 * -> 객체 생성을 스프링이 아닌 개발자가 직접 하는 것
	 * @return
	 */
	@Bean
	Sha createShaInstance() {
		Sha sha = new Sha();
		return sha;
	}
	
	/**
	 * JSP View Resolver 설정
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/views/",".jsp");
	}
	
	/**
	 * Static Resource 설정
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**") // http://localhost:8080/css/common/common.css - css 밑의 모든 경로
				.addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**") // http://localhost:8080/js/jquery/jquery-3.1.7.min.js
				.addResourceLocations("classpath:/static/js/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> excludeCheckSessionInterceptorsURL = new ArrayList<>();
		excludeCheckSessionInterceptorsURL.add("/js/**");
		excludeCheckSessionInterceptorsURL.add("/css/**");
		excludeCheckSessionInterceptorsURL.add("/image/**");
		excludeCheckSessionInterceptorsURL.add("/member/login");
		excludeCheckSessionInterceptorsURL.add("/member/regist/**");
		
		// First Interceptor
		registry.addInterceptor(new CheckSessionInterceptor())
				.addPathPatterns(this.checkSessionPathPatterns)
				.excludePathPatterns(this.checkSessionExcludePathPatterns);
		
		// Second Interceptor
		registry.addInterceptor(new CheckDuplicateLoginInterceptor())
				.addPathPatterns(this.checkDupLoginPathPatterns)
				.excludePathPatterns(this.checkDupLoginExcludePathPatterns);
		
		// Third Interceptor
		registry.addInterceptor(new AddAccessLogHistoryInterceptor(this.accessLogDao))
				.addPathPatterns(this.addAccessLogPathPatterns)
				.excludePathPatterns(this.addAccessLogExcludePathPatterns);
	}
}
