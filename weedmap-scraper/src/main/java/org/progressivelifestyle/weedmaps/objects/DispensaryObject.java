package org.progressivelifestyle.weedmaps.objects;

import java.util.HashSet;
import java.util.Set;

public class DispensaryObject implements Dispensary{
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
	private final String dispensaryURL;
	
	//private Map<String, Collection<MenuItem>> menuItems;
	private Set<MenuItem> menuItems;

	public DispensaryObject(Long dispensaryId,String name, String phone, String email, String website, Address address, String facebookURL, String twitterURL, String instagramURL, boolean creditCardSupport, boolean handicapSupport, boolean securityGuardSupport, boolean photoAvailable,
			boolean labTested, boolean forAdult, boolean deliverySupport, String sundayOpen, String sundayClose, String mondayOpen, String mondayClose, String tuesdayOpen, String tuesdayClose, String wednesdayOpen, String wednesdayClose, String thursdayOpen,
			String thursdayClose, String fridayOpen, String fridayClose, String saturdayOpen, String saturdayClose, String dispensaryURL) {
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
		this.dispensaryURL = dispensaryURL;
		//this.menuItems = new HashMap<String, Collection<MenuItem>>();
		this.menuItems = new HashSet<MenuItem>();
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


	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public int compareTo(Dispensary o) {
		return this.name.compareTo(o.getName());
	}

	public String getDispensaryURL() {
		return dispensaryURL;
	}

	@Override
	public String toString() {
		return "DispensaryObject [dispensaryURL=" + dispensaryURL + "]";
	}

	public Long getDispensaryId() {
		return dispensaryId;
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
		if (!dispensaryId.equals(other.dispensaryId))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (creditCardSupport != other.creditCardSupport)
			return false;
		if (deliverySupport != other.deliverySupport)
			return false;
		if (dispensaryURL == null) {
			if (other.dispensaryURL != null)
				return false;
		} else if (!dispensaryURL.equals(other.dispensaryURL))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (facebookURL == null) {
			if (other.facebookURL != null)
				return false;
		} else if (!facebookURL.equals(other.facebookURL))
			return false;
		if (forAdult != other.forAdult)
			return false;
		if (fridayClose == null) {
			if (other.fridayClose != null)
				return false;
		} else if (!fridayClose.equals(other.fridayClose))
			return false;
		if (fridayOpen == null) {
			if (other.fridayOpen != null)
				return false;
		} else if (!fridayOpen.equals(other.fridayOpen))
			return false;
		if (handicapSupport != other.handicapSupport)
			return false;
		if (instagramURL == null) {
			if (other.instagramURL != null)
				return false;
		} else if (!instagramURL.equals(other.instagramURL))
			return false;
		if (labTested != other.labTested)
			return false;
		if (mondayClose == null) {
			if (other.mondayClose != null)
				return false;
		} else if (!mondayClose.equals(other.mondayClose))
			return false;
		if (mondayOpen == null) {
			if (other.mondayOpen != null)
				return false;
		} else if (!mondayOpen.equals(other.mondayOpen))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (photoAvailable != other.photoAvailable)
			return false;
		if (saturdayClose == null) {
			if (other.saturdayClose != null)
				return false;
		} else if (!saturdayClose.equals(other.saturdayClose))
			return false;
		if (saturdayOpen == null) {
			if (other.saturdayOpen != null)
				return false;
		} else if (!saturdayOpen.equals(other.saturdayOpen))
			return false;
		if (securityGuardSupport != other.securityGuardSupport)
			return false;
		if (sundayClose == null) {
			if (other.sundayClose != null)
				return false;
		} else if (!sundayClose.equals(other.sundayClose))
			return false;
		if (sundayOpen == null) {
			if (other.sundayOpen != null)
				return false;
		} else if (!sundayOpen.equals(other.sundayOpen))
			return false;
		if (thursdayClose == null) {
			if (other.thursdayClose != null)
				return false;
		} else if (!thursdayClose.equals(other.thursdayClose))
			return false;
		if (thursdayOpen == null) {
			if (other.thursdayOpen != null)
				return false;
		} else if (!thursdayOpen.equals(other.thursdayOpen))
			return false;
		if (tuesdayClose == null) {
			if (other.tuesdayClose != null)
				return false;
		} else if (!tuesdayClose.equals(other.tuesdayClose))
			return false;
		if (tuesdayOpen == null) {
			if (other.tuesdayOpen != null)
				return false;
		} else if (!tuesdayOpen.equals(other.tuesdayOpen))
			return false;
		if (twitterURL == null) {
			if (other.twitterURL != null)
				return false;
		} else if (!twitterURL.equals(other.twitterURL))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
			return false;
		if (wednesdayClose == null) {
			if (other.wednesdayClose != null)
				return false;
		} else if (!wednesdayClose.equals(other.wednesdayClose))
			return false;
		if (wednesdayOpen == null) {
			if (other.wednesdayOpen != null)
				return false;
		} else if (!wednesdayOpen.equals(other.wednesdayOpen))
			return false;
		return true;
	}
	
}
