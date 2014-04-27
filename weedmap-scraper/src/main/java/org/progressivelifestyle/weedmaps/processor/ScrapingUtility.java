package org.progressivelifestyle.weedmaps.processor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.progressivelifestyle.weedmap.persistence.domain.Dispensary;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.DispensaryPictureEntity;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemCategoryEntity;
import org.progressivelifestyle.weedmap.persistence.domain.MenuItemEntity;
import org.progressivelifestyle.weedmap.persistence.service.DispensaryService;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.objects.MenuItem;
import org.progressivelifestyle.weedmaps.objects.Picture;

public class ScrapingUtility {
	public static void serializeToDisk(Collection<String> lines) throws IOException {
		String filePath = "C:/logs/URLList.txt";
		FileUtils.writeLines(new File(filePath), lines);
	}
	public static DispensaryEntity convertDispensaryIntoPersistenceObject(Dispensary dispensary, DispensaryService service){
		DispensaryEntity dispensaryEntity = new DispensaryEntity();
		dispensaryEntity.setCity(((DispensaryObject)dispensary).getAddress().getCity());
		dispensaryEntity.setCreditCardSupport(dispensary.isCreditCardSupport());
		dispensaryEntity.setDispensaryURL(dispensary.getDispensaryURL());
		dispensaryEntity.setEmail(dispensary.getEmail());
		dispensaryEntity.setFacebookURL(dispensary.getFacebookURL());
		dispensaryEntity.setForAdult(dispensary.isForAdult());
		dispensaryEntity.setFridayClose(dispensary.getFridayClose());
		dispensaryEntity.setFridayOpen(dispensary.getFridayOpen());
		dispensaryEntity.setHandicapSupport(dispensary.isHandicapSupport());
		dispensaryEntity.setId(dispensary.getDispensaryId());
		dispensaryEntity.setInstagramURL(dispensary.getInstagramURL());
		dispensaryEntity.setLabTested(dispensary.isLabTested());
		dispensaryEntity.setMondayClose(dispensary.getMondayClose());
		dispensaryEntity.setMondayOpen(dispensary.getMondayOpen());
		dispensaryEntity.setName(dispensary.getName());
		dispensaryEntity.setPhone(dispensary.getPhone());
		dispensaryEntity.setPhotoAvailable(dispensary.isPhotoAvailable());
		dispensaryEntity.setSaturdayClose(dispensary.getSaturdayClose());
		dispensaryEntity.setSaturdayOpen(dispensary.getSaturdayOpen());
		dispensaryEntity.setSecurityGuardSupport(dispensary.isSecurityGuardSupport());
		dispensaryEntity.setState(((DispensaryObject)dispensary).getAddress().getState());
		dispensaryEntity.setStreet(((DispensaryObject)dispensary).getAddress().getStreetAddress());
		dispensaryEntity.setStreet2(((DispensaryObject)dispensary).getAddress().getStreetAddress2());
		dispensaryEntity.setSundayClose(dispensary.getSundayClose());
		dispensaryEntity.setSundayOpen(dispensary.getSundayOpen());
		dispensaryEntity.setThursdayClose(dispensary.getThursdayClose());
		dispensaryEntity.setThursdayOpen(dispensary.getThursdayOpen());
		dispensaryEntity.setTuesdayClose(dispensary.getTuesdayClose());
		dispensaryEntity.setTuesdayOpen(dispensary.getTuesdayClose());
		dispensaryEntity.setTuesdayOpen(dispensary.getTuesdayOpen());
		dispensaryEntity.setWebsite(dispensary.getWebsite());
		dispensaryEntity.setWednesdayClose(dispensary.getWednesdayClose());
		dispensaryEntity.setWednesdayOpen(dispensary.getWednesdayOpen());
		dispensaryEntity.setZip(((DispensaryObject)dispensary).getAddress().getZip());
		
		Set<DispensaryPictureEntity> pictureEntities = new HashSet<DispensaryPictureEntity>(); 
		for(String picture : dispensary.getImages()){
			DispensaryPictureEntity pictureEntity = new DispensaryPictureEntity();
			pictureEntity.setPictureURL(picture);
			pictureEntities.add(pictureEntity);
		}
		dispensaryEntity.setPictures(pictureEntities);
		
		Set<Menu> menuItems = new HashSet<Menu>();
		Set<Menu> menuItemsFromUI = ((DispensaryObject)dispensary).getMenuItems();
		for(Menu item : menuItemsFromUI){
			MenuItemEntity itemEntity = new MenuItemEntity();
			itemEntity.setId(item.getId());
			itemEntity.setName(item.getName());
			itemEntity.setDescription(item.getDescription());
			itemEntity.setPriceEighth(item.getPriceEighth());
			itemEntity.setPriceGram(item.getPriceGram());
			itemEntity.setPriceHalfGram(item.getPriceHalfGram());
			itemEntity.setPriceHalfOunce(item.getPriceHalfOunce());
			itemEntity.setPriceOunce(item.getPriceOunce());
			itemEntity.setPriceQuarter(item.getPriceQuarter());
			itemEntity.setPriceUnit(item.getPriceUnit());
			itemEntity.setPictureURL(((MenuItem)item).getPictures()!=null && !((MenuItem)item).getPictures().isEmpty() && ((MenuItem)item).getPictures().iterator().next().getImage()!=null?  ((MenuItem)item).getPictures().iterator().next().getImage().getUrl():null);
			itemEntity.setStrainId(item.getStrainId());
			MenuItemCategoryEntity menuItemCategory = service.findMenuItemCategory(item.getMenuItemCategoryId());
			itemEntity.setMenuItemCategory(menuItemCategory);
			menuItems.add(itemEntity);
		}
		dispensaryEntity.setMenuItems(menuItems);
		return dispensaryEntity;
	}	
}
