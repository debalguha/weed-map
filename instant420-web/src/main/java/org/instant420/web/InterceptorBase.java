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
		logger.info("Interceptor -->preHandle-->"+request.getRequestURI());
		SearchType searchType = null;
		String requestURI = request.getRequestURI();
		logger.info("Request URI:: "+requestURI);
		if(requestURI.contains("search/dispensaries"))
			searchType =  SearchType.DISPENSARY;
		if(requestURI.contains("search/medicines"))
			searchType = SearchType.MEDICINE;
		if(requestURI.contains("search/popular"))
			searchType =  SearchType.POPULAR;
		if(requestURI.contains("search/hit"))
			searchType = SearchType.HIT;
//		if(requestURI.contains("search/advance"))
			searchType = SearchType.ADVANCED;	
		request.setAttribute("searchType", searchType);
		logger.info("Search Type:: "+searchType);
		return true;
	}
	
	public void setService(DispensaryService service) {
		this.service = service;
	}
	public static void main(String args[]) throws Exception{
		String search = "http://localhost:9080/instant420-web/rest/search/medicines?searchText=me&start=21&rows=10&key=instant420.rest.api";
		System.out.println(search.contains("search/medicines"));
	}
}
