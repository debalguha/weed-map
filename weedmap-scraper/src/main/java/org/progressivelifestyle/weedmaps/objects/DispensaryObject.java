package org.progressivelifestyle.weedmaps.objects;

import java.util.ArrayList;
import java.util.Collection;

public class DispensaryObject {
	private final Long dispensaryId;
	private final String name;
	private final String phone;
	private final String email;
	private final String website;
	private final Address address;
	private final String facebookURL;
	private final String twitterURL;
	private final String instagramURL;
	private final boolean creditCardSupport;
	private final boolean handicapSupport;
	private final boolean securityGuardSupport;
	private final boolean photoAvailable;
	private final boolean labTested;
	private final boolean forAdult;
	private final boolean deliverySupport;
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
	
	//private Map<String, Collection<MenuItem>> menuItems;
	private Collection<MenuItem> menuItems;

	public DispensaryObject(Long dispensaryId,String name, String phone, String email, String website, Address address, String facebookURL, String twitterURL, String instagramURL, boolean creditCardSupport, boolean handicapSupport, boolean securityGuardSupport, boolean photoAvailable,
			boolean labTested, boolean forAdult, boolean deliverySupport, String sundayOpen, String sundayClose, String mondayOpen, String mondayClose, String tuesdayOpen, String tuesdayClose, String wednesdayOpen, String wednesdayClose, String thursdayOpen,
			String thursdayClose, String fridayOpen, String fridayClose, String saturdayOpen, String saturdayClose) {
		super();
		this.dispensaryId = dispensaryId;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.website = website;
		this.address = address;
		this.facebookURL = facebookURL;
		this.twitterURL = twitterURL;
		this.instagramURL = instagramURL;
		this.creditCardSupport = creditCardSupport;
		this.handicapSupport = handicapSupport;
		this.securityGuardSupport = securityGuardSupport;
		this.photoAvailable = photoAvailable;
		this.labTested = labTested;
		this.forAdult = forAdult;
		this.deliverySupport = deliverySupport;
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
		//this.menuItems = new HashMap<String, Collection<MenuItem>>();
		this.menuItems = new ArrayList<MenuItem>();
	}

	public String getName() {
		return name;
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

	public Address getAddress() {
		return address;
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

	public boolean isCreditCardSupport() {
		return creditCardSupport;
	}

	public boolean isHandicapSupport() {
		return handicapSupport;
	}

	public boolean isSecurityGuardSupport() {
		return securityGuardSupport;
	}

	public boolean isPhotoAvailable() {
		return photoAvailable;
	}

	public boolean isLabTested() {
		return labTested;
	}

	public boolean isForAdult() {
		return forAdult;
	}

	public boolean isDeliverySupport() {
		return deliverySupport;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dispensaryId == null) ? 0 : dispensaryId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DispensaryObject other = (DispensaryObject) obj;
		if (dispensaryId == null) {
			if (other.dispensaryId != null)
				return false;
		} else if (!dispensaryId.equals(other.dispensaryId))
			return false;
		return true;
	}

	public Collection<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(Collection<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
}
