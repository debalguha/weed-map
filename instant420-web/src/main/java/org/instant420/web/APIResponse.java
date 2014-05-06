package org.instant420.web;

public class APIResponse {
	private final String responseString;
	private final Throwable cause;
	private final Long id;
	public String getResponseString() {
		return responseString;
	}
	public Throwable getCause() {
		return cause;
	}
	public APIResponse(Long id, String responseString, Throwable cause) {
		super();
		this.id = id;
		this.responseString = responseString;
		this.cause = cause;
	}
	public Long getId() {
		return id;
	}
	@Override
	public String toString() {
		return "APIResponse [responseString=" + responseString + ", cause=" + cause + ", id=" + id + "]";
	}
}
