package org.instant420.web.domain;

public class PopularSearchTermObject extends SearchObject {
	private final String searchText;
	private final String score;
	public PopularSearchTermObject(String searchText, String score) {
		super();
		this.searchText = searchText;
		this.score = score;
	}
	public String getSearchText() {
		return searchText;
	}
	public String getScore() {
		return score;
	}
	
}
