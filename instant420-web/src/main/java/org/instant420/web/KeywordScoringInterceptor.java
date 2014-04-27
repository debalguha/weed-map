package org.instant420.web;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.instant420.processor.ScoringKeywordProcessor;
import org.instant420.web.domain.SearchType;
import org.springframework.stereotype.Component;

@Component
public class KeywordScoringInterceptor extends InterceptorBase {

	private static final Log logger = LogFactory.getLog(KeywordScoringInterceptor.class);
	private Executor scoringSearchTermExecutor = new ThreadPoolExecutor(10, 20, Long.MAX_VALUE, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>());
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {
		logger.info("Interceptor -->afterCompletion");
		SearchType searchType = (SearchType)request.getAttribute("searchType");
		if(searchType.equals(SearchType.POPULAR))
			return;
		String searchText = request.getParameter("searchText");
		Boolean hasFound = request.getAttribute("hasFound")==null? new Boolean(false):(Boolean)request.getAttribute("hasFound");
		scoringSearchTermExecutor.execute(new ScoringKeywordProcessor(service, searchText, hasFound));
	}
}
