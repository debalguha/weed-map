package org.instant420.web.domain;

public class ResultMeta {
	private final int numFound;
	private final int start;
	private final int quantity;
	public ResultMeta(int numFound, int start, int quantity) {
		super();
		this.numFound = numFound;
		this.start = start;
		this.quantity = quantity;
	}
	public int getNumFound() {
		return numFound;
	}
	public int getStart() {
		return start;
	}
	public int getQuantity() {
		return quantity;
	}
	@Override
	public String toString() {
		return "ResultMeta [numFound=" + numFound + ", start=" + start + ", quantity=" + quantity + "]";
	}
}
