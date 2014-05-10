package org.instant420.web.domain;

import java.util.ArrayList;
import java.util.Collection;

public class ResultMeta {
	private long numFound;
	private long start;
	private int quantity;
	private Collection<SearchObject> searchResults;
	
	public ResultMeta(){
		
	}
	
	public ResultMeta(long numFound, long start, int quantity) {
		super();
		this.numFound = numFound;
		this.start = start;
		this.quantity = quantity;
		searchResults = new ArrayList<SearchObject>();
	}
	public long getNumFound() {
		return numFound;
	}
	public long getStart() {
		return start;
	}
	public int getQuantity() {
		return quantity;
	}
	@Override
	public String toString() {
		return "ResultMeta [numFound=" + numFound + ", start=" + start + ", quantity=" + quantity + "]";
	}
	public Collection<SearchObject> getSearchResults() {
		return searchResults;
	}
	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setSearchResults(Collection<SearchObject> searchResults) {
		this.searchResults = searchResults;
	}
}
