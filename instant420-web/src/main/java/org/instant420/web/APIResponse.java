package org.instant420.web;


public class APIResponse {
	private String responseString;
	private Throwable cause;
	private Long id;
	public APIResponse(){
		
	}
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
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
