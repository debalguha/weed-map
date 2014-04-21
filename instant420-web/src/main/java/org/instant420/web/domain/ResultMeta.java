package org.instant420.web.domain;

import java.util.ArrayList;
import java.util.Collection;

public class ResultMeta {
	private final long numFound;
	private final long start;
	private final int quantity;
	private final Collection<SearchObject> searchResults;
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
}
