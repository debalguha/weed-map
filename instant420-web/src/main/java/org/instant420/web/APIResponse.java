package org.instant420.web;

public class APIResponse {
	private final String responseString;
	private final Throwable cause;
	public String getResponseString() {
		return responseString;
	}
	public Throwable getCause() {
		return cause;
	}
	public APIResponse(String responseString, Throwable cause) {
		super();
		this.responseString = responseString;
		this.cause = cause;
	}
	@Override
	public String toString() {
		return "APIResponse [responseString=" + responseString + ", cause=" + cause + "]";
	}
	
}
