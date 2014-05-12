package org.instant420.web.domain;

public class DispensarySearchObject extends SearchObject{
	private final String id;
	private final String name;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private final String phone;
	private final String email;
	private final String website;
	private final String facebookURL;
	private final String twitterURL;
	private final String instagramURL;
	private final String sundayOpen;
	private final String sundayClose;
	private final String mondayOpen;
	private final String mondayClose;
	private final String tuesdayOpen;
	private final String tuesdayClose;
	private final String wednesdayOpen;
	private final String wednesdayClose;
	private final String thursdayOpen;
	private final String thursdayClose;
	private final String fridayOpen;
	private final String fridayClose;
	private final String saturdayOpen;
	private final String saturdayClose;
	private final String dispensaryURL;
	private final String lat;
	private final String lng;
	private String dispensaryImageURL;
	private Double distanceInKM;
	private double distanceInMiles;
	public DispensarySearchObject(String id, String name, String street, String city, String state, String zip, String phone, String email, String website, String facebookURL, String twitterURL, String instagramURL, String sundayOpen, String sundayClose, 
			String mondayOpen, String mondayClose, String tuesdayOpen, String tuesdayClose, String wednesdayOpen, String wednesdayClose, String thursdayOpen, String thursdayClose, String fridayOpen, String fridayClose, String saturdayOpen, 
			String saturdayClose, String dispensaryURL, String dispensaryImageURL, Double distanceInKM, Double distanceInMiles, String lat, String lng) {
		super();
		this.id = id;
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
		this.website = website;
		this.facebookURL = facebookURL;
		this.twitterURL = twitterURL;
		this.instagramURL = instagramURL;
		this.sundayOpen = sundayOpen;
		this.sundayClose = sundayClose;
		this.mondayOpen = mondayOpen;
		this.mondayClose = mondayClose;
		this.tuesdayOpen = tuesdayOpen;
		this.tuesdayClose = tuesdayClose;
		this.wednesdayOpen = wednesdayOpen;
		this.wednesdayClose = wednesdayClose;
		this.thursdayOpen = thursdayOpen;
		this.thursdayClose = thursdayClose;
		this.fridayOpen = fridayOpen;
		this.fridayClose = fridayClose;
		this.saturdayOpen = saturdayOpen;
		this.saturdayClose = saturdayClose;
		this.dispensaryURL = dispensaryURL;
		this.dispensaryImageURL = dispensaryImageURL;
		this.distanceInKM = distanceInKM;
		this.distanceInMiles = distanceInMiles;
		this.lat = lat;
		this.lng = lng;
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
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getWebsite() {
		return website;
	}
	public String getFacebookURL() {
		return facebookURL;
	}
	public String getTwitterURL() {
		return twitterURL;
	}
	public String getInstagramURL() {
		return instagramURL;
	}
	public String getSundayOpen() {
		return sundayOpen;
	}
	public String getSundayClose() {
		return sundayClose;
	}
	public String getMondayOpen() {
		return mondayOpen;
	}
	public String getMondayClose() {
		return mondayClose;
	}
	public String getTuesdayOpen() {
		return tuesdayOpen;
	}
	public String getTuesdayClose() {
		return tuesdayClose;
	}
	public String getWednesdayOpen() {
		return wednesdayOpen;
	}
	public String getWednesdayClose() {
		return wednesdayClose;
	}
	public String getThursdayOpen() {
		return thursdayOpen;
	}
	public String getThursdayClose() {
		return thursdayClose;
	}
	public String getFridayOpen() {
		return fridayOpen;
	}
	public String getFridayClose() {
		return fridayClose;
	}
	public String getSaturdayOpen() {
		return saturdayOpen;
	}
	public String getSaturdayClose() {
		return saturdayClose;
	}
	public String getDispensaryURL() {
		return dispensaryURL;
	}
	public String getDispensaryImageURL() {
		return dispensaryImageURL;
	}
	public Double getDistanceInKM() {
		return distanceInKM;
	}
	public double getDistanceInMiles() {
		return distanceInMiles;
	}
	public String getLat() {
		return lat;
	}
	public String getLng() {
		return lng;
	}
	
}
