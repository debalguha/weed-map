package org.instant420.web.domain;

public class DispensarySearchObject extends SearchObject{
	private final String id;
	private final String name;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	public DispensarySearchObject(String id, String name, String street, String city, String state, String zip) {
		super();
		this.id = id;
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
}
