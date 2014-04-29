package org.instant420.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.instant420.web.domain.SearchType;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public abstract class InterceptorBase implements HandlerInterceptor{
	@Autowired
	protected DispensaryService service;
	protected static final Log logger= LogFactory.getLog(InterceptorBase.class);
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {
		logger.info("Interceptor -->postHandle");
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		logger.info("Interceptor -->preHandle");
		String requestURI = request.getRequestURI();
		if(requestURI.contains("search/dispensary"))
			request.setAttribute("searchType", SearchType.DISPENSARY);
		if(requestURI.contains("search/medicine"))
			request.setAttribute("searchType", SearchType.MEDICINE);
		if(requestURI.contains("search/popular"))
			request.setAttribute("searchType", SearchType.POPULAR);		
		return true;
	}
	
	public void setService(DispensaryService service) {
		this.service = service;
	}
	
}