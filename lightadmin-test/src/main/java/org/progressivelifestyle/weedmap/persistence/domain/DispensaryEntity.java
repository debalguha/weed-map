package org.progressivelifestyle.weedmap.persistence.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="dispensaryentity")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DispensaryEntity implements Comparable<DispensaryEntity>{
	@Id
	private Long id;
	private String name;
	private String phone;
	private String email;
	private String website;
	private String street;
	private String street2;
	private String city;
	private String state;
	private String zip;
	private String facebookURL;
	private String twitterURL;
	private String instagramURL;
	private boolean creditCardSupport;
	private boolean handicapSupport;
	private boolean securityGuardSupport;
	private boolean photoAvailable;
	private boolean labTested;
	private boolean forAdult;
	private boolean deliverySupport;
	private String sundayOpen;
	private String sundayClose;
	private String mondayOpen;
	private String mondayClose;
	private String tuesdayOpen;
	private String tuesdayClose;
	private String wednesdayOpen;
	private String wednesdayClose;
	private String thursdayOpen;
	private String thursdayClose;
	private String fridayOpen;
	private String fridayClose;
	private String saturdayOpen;
	private String saturdayClose;
	private String dispensaryURL;
	private String dispensaryImageURL;
	
	@Column(nullable = true)
	private BigDecimal lat;
	@Column(nullable = true)
	private BigDecimal lang;
	
	private Date creationDate;
	private Date lastUpdateDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long dispensaryId) {
		this.id = dispensaryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getFacebookURL() {
		return facebookURL;
	}
	public void setFacebookURL(String facebookURL) {
		this.facebookURL = facebookURL;
	}
	public String getTwitterURL() {
		return twitterURL;
	}
	public void setTwitterURL(String twitterURL) {
		this.twitterURL = twitterURL;
	}
	public String getInstagramURL() {
		return instagramURL;
	}
	public void setInstagramURL(String instagramURL) {
		this.instagramURL = instagramURL;
	}
	public boolean isCreditCardSupport() {
		return creditCardSupport;
	}
	public void setCreditCardSupport(boolean creditCardSupport) {
		this.creditCardSupport = creditCardSupport;
	}
	public boolean isHandicapSupport() {
		return handicapSupport;
	}
	public void setHandicapSupport(boolean handicapSupport) {
		this.handicapSupport = handicapSupport;
	}
	public boolean isSecurityGuardSupport() {
		return securityGuardSupport;
	}
	public void setSecurityGuardSupport(boolean securityGuardSupport) {
		this.securityGuardSupport = securityGuardSupport;
	}
	public boolean isPhotoAvailable() {
		return photoAvailable;
	}
	public void setPhotoAvailable(boolean photoAvailable) {
		this.photoAvailable = photoAvailable;
	}
	public boolean isLabTested() {
		return labTested;
	}
	public void setLabTested(boolean labTested) {
		this.labTested = labTested;
	}
	public boolean isForAdult() {
		return forAdult;
	}
	public void setForAdult(boolean forAdult) {
		this.forAdult = forAdult;
	}
	public boolean isDeliverySupport() {
		return deliverySupport;
	}
	public void setDeliverySupport(boolean deliverySupport) {
		this.deliverySupport = deliverySupport;
	}
	public String getSundayOpen() {
		return sundayOpen;
	}
	public void setSundayOpen(String sundayOpen) {
		this.sundayOpen = sundayOpen;
	}
	public String getSundayClose() {
		return sundayClose;
	}
	public void setSundayClose(String sundayClose) {
		this.sundayClose = sundayClose;
	}
	public String getMondayOpen() {
		return mondayOpen;
	}
	public void setMondayOpen(String mondayOpen) {
		this.mondayOpen = mondayOpen;
	}
	public String getMondayClose() {
		return mondayClose;
	}
	public void setMondayClose(String mondayClose) {
		this.mondayClose = mondayClose;
	}
	public String getTuesdayOpen() {
		return tuesdayOpen;
	}
	public void setTuesdayOpen(String tuesdayOpen) {
		this.tuesdayOpen = tuesdayOpen;
	}
	public String getTuesdayClose() {
		return tuesdayClose;
	}
	public void setTuesdayClose(String tuesdayClose) {
		this.tuesdayClose = tuesdayClose;
	}
	public String getWednesdayOpen() {
		return wednesdayOpen;
	}
	public void setWednesdayOpen(String wednesdayOpen) {
		this.wednesdayOpen = wednesdayOpen;
	}
	public String getWednesdayClose() {
		return wednesdayClose;
	}
	public void setWednesdayClose(String wednesdayClose) {
		this.wednesdayClose = wednesdayClose;
	}
	public String getThursdayOpen() {
		return thursdayOpen;
	}
	public void setThursdayOpen(String thursdayOpen) {
		this.thursdayOpen = thursdayOpen;
	}
	public String getThursdayClose() {
		return thursdayClose;
	}
	public void setThursdayClose(String thursdayClose) {
		this.thursdayClose = thursdayClose;
	}
	public String getFridayOpen() {
		return fridayOpen;
	}
	public void setFridayOpen(String fridayOpen) {
		this.fridayOpen = fridayOpen;
	}
	public String getFridayClose() {
		return fridayClose;
	}
	public void setFridayClose(String fridayClose) {
		this.fridayClose = fridayClose;
	}
	public String getSaturdayOpen() {
		return saturdayOpen;
	}
	public void setSaturdayOpen(String saturdayOpen) {
		this.saturdayOpen = saturdayOpen;
	}
	public String getSaturdayClose() {
		return saturdayClose;
	}
	public void setSaturdayClose(String saturdayClose) {
		this.saturdayClose = saturdayClose;
	}
	public String getDispensaryURL() {
		return dispensaryURL;
	}
	public void setDispensaryURL(String dispensaryURL) {
		this.dispensaryURL = dispensaryURL;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DispensaryEntity other = (DispensaryEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public int compareTo(DispensaryEntity o) {
		return this.id.compareTo(o.getDispensaryId());
	}
	public Long getDispensaryId() {
		return getId();
	}
	public boolean isLogicallyEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DispensaryEntity other = (DispensaryEntity) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
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
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		if (street2 == null) {
			if (other.street2 != null)
				return false;
		} else if (!street2.equals(other.street2))
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
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	public BigDecimal getLang() {
		return lang;
	}
	public void setLang(BigDecimal lang) {
		this.lang = lang;
	}
}
