package org.instant420.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class Instant420AuthFilter implements Filter{
	private String filterKey;
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String apiKey = request.getParameter("key");
		if(apiKey.equals(filterKey))
			chain.doFilter(request, response);
		((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unrecognized API Key");
	}

	public void init(FilterConfig config) throws ServletException {
		filterKey = config.getServletContext().getInitParameter("instant420.api.key");
	}

}
