package org.progressivelifestyle.weedmap.persistence.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface Dispensary extends Comparable<Dispensary>{

	public abstract String getName();

	public abstract String getPhone();

	public abstract String getEmail();

	public abstract String getWebsite();

	//public abstract Address getAddress();

	public abstract String getFacebookURL();

	public abstract String getTwitterURL();

	public abstract String getInstagramURL();

	public abstract boolean isCreditCardSupport();

	public abstract boolean isHandicapSupport();

	public abstract boolean isSecurityGuardSupport();

	public abstract boolean isPhotoAvailable();

	public abstract boolean isLabTested();

	public abstract boolean isForAdult();

	public abstract boolean isDeliverySupport();

	public abstract String getSundayOpen();

	public abstract String getSundayClose();

	public abstract String getMondayOpen();

	public abstract String getMondayClose();

	public abstract String getTuesdayOpen();

	public abstract String getTuesdayClose();

	public abstract String getWednesdayOpen();

	public abstract String getWednesdayClose();

	public abstract String getThursdayOpen();

	public abstract String getThursdayClose();

	public abstract String getFridayOpen();

	public abstract String getFridayClose();

	public abstract String getSaturdayOpen();

	public abstract String getSaturdayClose();

	public abstract Set<Menu> getMenuItems();

	public abstract String getDispensaryURL();

	public abstract Long getDispensaryId();
	
	public void setMenuItems(Set<Menu> menus);
	
	public List<String> getImages();
	
	public BigDecimal getLat();
	
	public BigDecimal getLang();
	
	public String getDispensaryImageURL();
}