package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
public class DispensaryEntity implements BaseEntity{
	@Id
	private Long id;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false, unique = true)
	private String phone;
	@Column(nullable=false, unique = true)
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
	
	private Date creationDate;
	private Date lastUpdateDate;
	
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value={CascadeType.ALL})
	@JoinColumn(name="dispensaryId", referencedColumnName="id")
	private Set<MenuItemEntity> menuItems;
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
	public Set<MenuItemEntity> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(Set<MenuItemEntity> menuItems) {
		this.menuItems = menuItems;
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
}
