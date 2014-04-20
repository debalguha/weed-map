package org.progressivelifestyle.weedmaps.objects;

import java.util.Set;

import org.progressivelifestyle.weedmap.persistence.domain.Dispensary;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;

public class ChangeIndicatingDispensaryObject extends DispensaryObject implements Dispensary{
	private final Dispensary actualDispensary;
	private Set<MenuItem> menuItemsToBeAdded;
	private Set<MenuItem> menuItemsToBeRemoved;
	private Set<MenuItem> menuItemstobeUpdated;
	private boolean  changeInDispensary;
	public ChangeIndicatingDispensaryObject(Dispensary actualDispensary) {
		super();
		this.actualDispensary = actualDispensary;
	}
	public int compareTo(Dispensary o) {
		return actualDispensary.compareTo(((ChangeIndicatingDispensaryObject)o).actualDispensary);
	}
	public String getName() {
		return actualDispensary.getName();
	}
	public String getPhone() {
		return actualDispensary.getPhone();
	}
	public String getEmail() {
		return actualDispensary.getEmail();
	}
	public String getWebsite() {
		return actualDispensary.getWebsite();
	}
	public Address getAddress() {
		return ((DispensaryObject)actualDispensary).getAddress();
	}
	public String getFacebookURL() {
		return actualDispensary.getFacebookURL();
	}
	public String getTwitterURL() {
		return actualDispensary.getTwitterURL();
	}
	public String getInstagramURL() {
		return actualDispensary.getInstagramURL();
	}
	public boolean isCreditCardSupport() {
		return actualDispensary.isCreditCardSupport();
	}
	public boolean isHandicapSupport() {
		return actualDispensary.isHandicapSupport();
	}
	public boolean isSecurityGuardSupport() {
		return actualDispensary.isSecurityGuardSupport();
	}
	public boolean isPhotoAvailable() {
		return actualDispensary.isPhotoAvailable();
	}
	public boolean isLabTested() {
		return actualDispensary.isLabTested();
	}
	public boolean isForAdult() {
		return actualDispensary.isForAdult();
	}
	public boolean isDeliverySupport() {
		return actualDispensary.isDeliverySupport();
	}
	public String getSundayOpen() {
		return actualDispensary.getSaturdayOpen();
	}
	public String getSundayClose() {
		return actualDispensary.getSundayClose();
	}
	public String getMondayOpen() {
		return actualDispensary.getMondayOpen();
	}
	public String getMondayClose() {
		return actualDispensary.getMondayClose();
	}
	public String getTuesdayOpen() {
		return actualDispensary.getTuesdayOpen();
	}
	public String getTuesdayClose() {
		return actualDispensary.getTuesdayClose();
	}
	public String getWednesdayOpen() {
		return actualDispensary.getWednesdayOpen();
	}
	public String getWednesdayClose() {
		return actualDispensary.getWednesdayClose();
	}
	public String getThursdayOpen() {
		return actualDispensary.getThursdayOpen();
	}
	public String getThursdayClose() {
		return actualDispensary.getThursdayClose();
	}
	public String getFridayOpen() {
		return actualDispensary.getFridayOpen();
	}
	public String getFridayClose() {
		return actualDispensary.getFridayClose();
	}
	public String getSaturdayOpen() {
		return actualDispensary.getSaturdayOpen();
	}
	public String getSaturdayClose() {
		return actualDispensary.getSaturdayClose();
	}
	public Set<Menu> getMenuItems() {
		return ((DispensaryObject)actualDispensary).getMenuItems();
	}
	public String getDispensaryURL() {
		return actualDispensary.getDispensaryURL();
	}
	public Long getDispensaryId() {
		return actualDispensary.getDispensaryId();
	}
	public Dispensary getActualDispensary() {
		return actualDispensary;
	}
	public boolean isChangeInDispensary() {
		return changeInDispensary;
	}
	public void setChangeInDispensary(boolean changeInDispensary) {
		this.changeInDispensary = changeInDispensary;
	}
	public Set<MenuItem> getMenuItemsToBeAdded() {
		return menuItemsToBeAdded;
	}
	public void setMenuItemsToBeAdded(Set<MenuItem> menuItemsToBeAdded) {
		this.menuItemsToBeAdded = menuItemsToBeAdded;
	}
	public Set<MenuItem> getMenuItemsToBeRemoved() {
		return menuItemsToBeRemoved;
	}
	public void setMenuItemsToBeRemoved(Set<MenuItem> menuItemsToBeRemoved) {
		this.menuItemsToBeRemoved = menuItemsToBeRemoved;
	}
	public Set<MenuItem> getMenuItemstobeUpdated() {
		return menuItemstobeUpdated;
	}
	public void setMenuItemstobeUpdated(Set<MenuItem> menuItemstobeUpdated) {
		this.menuItemstobeUpdated = menuItemstobeUpdated;
	}

}
