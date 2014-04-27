package org.instant420.processor;

import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;

public class ScoringKeywordProcessor implements Runnable{
	private DispensaryService service;
	private String searchText;
	private boolean hasFound;
	
	public ScoringKeywordProcessor(DispensaryService service, String searchText, Boolean hasFound) {
		super();
		this.service = service;
		this.searchText = searchText;
		this.hasFound = hasFound;
	}

	public void run() {
		service.createOrUpdateScore(searchText, hasFound);
	}

}
