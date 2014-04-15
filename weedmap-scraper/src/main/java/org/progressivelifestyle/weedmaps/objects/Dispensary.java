package org.progressivelifestyle.weedmaps.objects;

import java.util.Set;

public interface Dispensary extends Comparable<Dispensary>{

	public abstract String getName();

	public abstract String getPhone();

	public abstract String getEmail();

	public abstract String getWebsite();

	public abstract Address getAddress();

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

	public abstract Set<MenuItem> getMenuItems();

	public abstract String getDispensaryURL();

	public abstract Long getDispensaryId();

}