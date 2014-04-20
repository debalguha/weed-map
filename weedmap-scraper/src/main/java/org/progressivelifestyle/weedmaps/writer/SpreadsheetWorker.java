package org.progressivelifestyle.weedmaps.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.progressivelifestyle.weedmap.persistence.domain.Dispensary;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;
import org.progressivelifestyle.weedmaps.objects.Address;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.objects.MenuItem;
import org.progressivelifestyle.weedmaps.objects.Picture;
import org.progressivelifestyle.weedmaps.objects.PictureImage;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

public class SpreadsheetWorker {
	private static Set<Long> menuItemIdsCached = new HashSet<Long>();
	private static Set<Long> dispensaryIdsCached = new HashSet<Long>();
	public static Collection<DispensaryObject> readSpreadsheetFromGoogleDrive(String spreadsheetName) throws Exception {
		Collection<DispensaryObject> dispensaries = new TreeSet<DispensaryObject>();
	    SpreadsheetService service = new SpreadsheetService(spreadsheetName);
	    WorksheetEntry[] worksheetEntries = assignStoresheetAndMenusheet(spreadsheetName, service);
	    WorksheetEntry storeSheet = worksheetEntries[0];
	    WorksheetEntry menuSheet = worksheetEntries[1];
		Map<Long, DispensaryObject> dispensaryMap = readListFeedForDispensariesAndCreateMap(storeSheet, service);
		readListFeedFormenuItemsAndAttachToDispensary(menuSheet, service, dispensaryMap);
		dispensaries.addAll(dispensaryMap.values());
	    return dispensaries;
	}
	private static Map<Long, DispensaryObject> readListFeedForDispensariesAndCreateMap(WorksheetEntry worksheet, SpreadsheetService service) throws IOException, ServiceException {
		Map<Long, DispensaryObject> storeSheetMap = new HashMap<Long, DispensaryObject>();
		URL listFeedUrl = worksheet.getListFeedUrl();
	    ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
	    for (ListEntry row : listFeed.getEntries()) {
	    	DispensaryObject dispensary = createDispensaryFromSpreadsheetRow(row);
			storeSheetMap.put(dispensary.getDispensaryId(), dispensary);
	    }
		return storeSheetMap;
	}
	private static WorksheetEntry[] assignStoresheetAndMenusheet(String spreadsheetName, SpreadsheetService service) throws Exception{
		WorksheetEntry[] worksheetEntries = new WorksheetEntry[2];
		String USERNAME = "debalguha.active@gmail.com";
		String PASSWORD = "123$Debal";
		service.setProtocolVersion(SpreadsheetService.Versions.V3);
		service.setUserCredentials(USERNAME, PASSWORD);
		URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		SpreadsheetEntry spreadsheet = null;
		spreadsheets.get(1);
		for(SpreadsheetEntry entry : spreadsheets){
			if(entry.getTitle().getPlainText().equals(spreadsheetName)){
				spreadsheet = entry;
						break;
			}
		}
	    System.out.println(spreadsheet.getTitle().getPlainText());
	    List<WorksheetEntry> worksheets = spreadsheet.getWorksheets();
	    for(WorksheetEntry worksheet : worksheets) {
	    	if(worksheet.getTitle().getPlainText().equals("Dispensary"))
	    		worksheetEntries[0] = worksheet;
	    	else if(worksheet.getTitle().getPlainText().equals("Menu"))
	    		worksheetEntries[1] = worksheet;
	    }
	    return worksheetEntries;
	}
	private static void readListFeedFormenuItemsAndAttachToDispensary(WorksheetEntry worksheet, SpreadsheetService service, Map<Long, DispensaryObject> dispensaryMap) throws IOException, ServiceException {
		URL listFeedUrl = worksheet.getListFeedUrl();
		ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
		for (ListEntry row : listFeed.getEntries()) {
			String name = row.getCustomElements().getValue("name");
			String strainId = row.getCustomElements().getValue("strainid");
			boolean inStock = true;
			String image = row.getCustomElements().getValue("image");
			Long menuItemCategoryId = Long.parseLong(row.getCustomElements().getValue("menuitemcategoryid")); 
			Long id = Long.parseLong(row.getCustomElements().getValue("menuitemid")); 
			Long dispensaryId = Long.parseLong(row.getCustomElements().getValue("dispensaryid")); 
			String description = row.getCustomElements().getValue("description");
			int unitdonation = row.getCustomElements().getValue("unitdonation")==null?0:Integer.parseInt(row.getCustomElements().getValue("unitdonation"));
			int gramdonation = row.getCustomElements().getValue("gramdonation")==null?0:Integer.parseInt(row.getCustomElements().getValue("gramdonation"));
			int eighthdonation = row.getCustomElements().getValue("eighthdonation")==null?0:Integer.parseInt(row.getCustomElements().getValue("eighthdonation"));
			int quarterdonation = row.getCustomElements().getValue("quarterdonation")==null?0:Integer.parseInt(row.getCustomElements().getValue("quarterdonation"));
			int halfdonation = row.getCustomElements().getValue("halfdonation")==null?0:Integer.parseInt(row.getCustomElements().getValue("halfdonation"));
			int halfouncedonation = row.getCustomElements().getValue("halfouncedonation")==null?0:Integer.parseInt(row.getCustomElements().getValue("halfouncedonation"));
			int ouncedonation = row.getCustomElements().getValue("ouncedonation")==null?0:Integer.parseInt(row.getCustomElements().getValue("ouncedonation"));

			MenuItem item = new MenuItem();
			item.setName(name);
			item.setStrainId(strainId);
			item.setInStock(inStock);
			if(image!=null || !"".equals(image)){
				Collection<Picture> pictures = new ArrayList<Picture>(1);
				Picture pic = new Picture();
				PictureImage picImage = new PictureImage();
				pic.setImage(picImage);
				pictures.add(pic);
			}
			
			item.setMenuItemCategoryId(menuItemCategoryId);
			item.setId(id);
			item.setDispensaryId(dispensaryId);
			item.setDescription(description);
			item.setPriceUnit(unitdonation);
			item.setPriceGram(gramdonation);
			item.setPriceEighth(eighthdonation);
			item.setPriceHalfGram(halfdonation);
			item.setPriceQuarter(quarterdonation);
			item.setPriceHalfOunce(halfouncedonation);
			item.setPriceOunce(ouncedonation);
			dispensaryMap.get(dispensaryId).getMenuItems().add(item);
		}
		
	}
 	private static void populateDispensaryInRow(ListEntry row, Dispensary dispensary){
    	row.getCustomElements().setValueLocal("name", dispensary.getName());
    	row.getCustomElements().setValueLocal("phone", dispensary.getPhone());
    	row.getCustomElements().setValueLocal("website", dispensary.getWebsite());
    	row.getCustomElements().setValueLocal("email", dispensary.getEmail());
    	row.getCustomElements().setValueLocal("street", ((DispensaryObject)dispensary).getAddress().getStreetAddress());
    	row.getCustomElements().setValueLocal("street2", "");
    	row.getCustomElements().setValueLocal("city", ((DispensaryObject)dispensary).getAddress().getCity());
    	row.getCustomElements().setValueLocal("state", ((DispensaryObject)dispensary).getAddress().getState());
    	row.getCustomElements().setValueLocal("zipcode", ((DispensaryObject)dispensary).getAddress().getZip());
    	row.getCustomElements().setValueLocal("logo", "");
    	row.getCustomElements().setValueLocal("promoimage", "");
    	row.getCustomElements().setValueLocal("facebook", dispensary.getFacebookURL());
    	row.getCustomElements().setValueLocal("twitter", dispensary.getTwitterURL());
    	row.getCustomElements().setValueLocal("instagram", dispensary.getInstagramURL());
    	row.getCustomElements().setValueLocal("intro", dispensary.getInstagramURL());
    	row.getCustomElements().setValueLocal("description", dispensary.getInstagramURL());
    	row.getCustomElements().setValueLocal("announcement", dispensary.getInstagramURL());
    	row.getCustomElements().setValueLocal("firsttimeannouncement", dispensary.getInstagramURL());
    	row.getCustomElements().setValueLocal("creditcard", dispensary.isCreditCardSupport()?"X":"");
    	row.getCustomElements().setValueLocal("handicapaccessible", dispensary.isHandicapSupport()?"X":"");
    	row.getCustomElements().setValueLocal("securityguard", dispensary.isSecurityGuardSupport()?"X":"");
    	row.getCustomElements().setValueLocal("photos", dispensary.isPhotoAvailable()?"X":"");
    	row.getCustomElements().setValueLocal("labtested", dispensary.isLabTested()?"X":"");
    	row.getCustomElements().setValueLocal("foradult", dispensary.isForAdult()?"X":"");
    	row.getCustomElements().setValueLocal("delivery", dispensary.isDeliverySupport()?"X":"");
    	row.getCustomElements().setValueLocal("openmon", dispensary.getMondayOpen());
    	row.getCustomElements().setValueLocal("closemon", dispensary.getMondayClose());
    	row.getCustomElements().setValueLocal("opentues", dispensary.getTuesdayOpen());
    	row.getCustomElements().setValueLocal("closetues", dispensary.getTuesdayClose());
    	row.getCustomElements().setValueLocal("openwed", dispensary.getWednesdayOpen());
    	row.getCustomElements().setValueLocal("closewed", dispensary.getWednesdayClose());
    	row.getCustomElements().setValueLocal("openthu", dispensary.getThursdayOpen());
    	row.getCustomElements().setValueLocal("closethu", dispensary.getThursdayClose());
    	row.getCustomElements().setValueLocal("openfri", dispensary.getFridayOpen());
    	row.getCustomElements().setValueLocal("closefri", dispensary.getFridayClose());
    	row.getCustomElements().setValueLocal("opensat", dispensary.getSaturdayOpen());
    	row.getCustomElements().setValueLocal("closesat", dispensary.getSaturdayClose());
    	row.getCustomElements().setValueLocal("opensun", dispensary.getSundayOpen());
    	row.getCustomElements().setValueLocal("closesun", dispensary.getSundayClose());
    	row.getCustomElements().setValueLocal("weedmapsurl", dispensary.getSundayClose());
    	row.getCustomElements().setValueLocal("dispensaryid", String.valueOf(dispensary.getDispensaryId()));
	}
	private static void populateMenuItemInRow(ListEntry rowMenu, Menu item, Dispensary dispensary){
		rowMenu.getCustomElements().setValueLocal("dispensary", dispensary.getName());
		rowMenu.getCustomElements().setValueLocal("name", item.getName());
		rowMenu.getCustomElements().setValueLocal("strainid", item.getStrainId()==null?"":item.getStrainId());
		rowMenu.getCustomElements().setValueLocal("instock", "X");
		rowMenu.getCustomElements().setValueLocal("soldout", "");
		rowMenu.getCustomElements().setValueLocal("image", ((MenuItem)item).getPictures()!=null && !((MenuItem)item).getPictures().isEmpty() && ((MenuItem)item).getPictures().iterator().next().getImage()!=null?((MenuItem)item).getPictures().iterator().next().getImage().getUrl():"");
		rowMenu.getCustomElements().setValueLocal("flowers", item.getMenuItemCategoryId()==1 || item.getMenuItemCategoryId()==2 ? "X":"");

		rowMenu.getCustomElements().setValueLocal("indica", item.getMenuItemCategoryId()==1?"X":"");
		rowMenu.getCustomElements().setValueLocal("sativa", item.getMenuItemCategoryId()==2?"X":"");
		rowMenu.getCustomElements().setValueLocal("hybrid", item.getMenuItemCategoryId()==3?"X":"");
		rowMenu.getCustomElements().setValueLocal("edibles", item.getMenuItemCategoryId()==4?"X":"");
		rowMenu.getCustomElements().setValueLocal("concentrates", item.getMenuItemCategoryId()==5?"X":"");
		rowMenu.getCustomElements().setValueLocal("drink", item.getMenuItemCategoryId()==6?"X":"");
		rowMenu.getCustomElements().setValueLocal("clones", item.getMenuItemCategoryId()==7?"X":"");
		rowMenu.getCustomElements().setValueLocal("seeds", item.getMenuItemCategoryId()==8?"X":"");
		rowMenu.getCustomElements().setValueLocal("tincture", item.getMenuItemCategoryId()==9?"X":"");
		rowMenu.getCustomElements().setValueLocal("gear", item.getMenuItemCategoryId()==10?"X":"");
		rowMenu.getCustomElements().setValueLocal("accessories", item.getMenuItemCategoryId()==10?"X":"");
		rowMenu.getCustomElements().setValueLocal("topicals", item.getMenuItemCategoryId()==11?"X":"");
		rowMenu.getCustomElements().setValueLocal("preroll", item.getMenuItemCategoryId()==12?"X":"");
		rowMenu.getCustomElements().setValueLocal("wax", item.getMenuItemCategoryId()==13?"X":"");

		rowMenu.getCustomElements().setValueLocal("description", item.getDescription());
		rowMenu.getCustomElements().setValueLocal("unitdonation", String.valueOf(item.getPriceUnit()));
		rowMenu.getCustomElements().setValueLocal("gramdonation", String.valueOf(item.getPriceGram()));
		rowMenu.getCustomElements().setValueLocal("eighthdonation", String.valueOf(item.getPriceEighth()));
		rowMenu.getCustomElements().setValueLocal("quarterdonation", String.valueOf(item.getPriceQuarter()));
		rowMenu.getCustomElements().setValueLocal("halfouncedonation", String.valueOf(item.getPriceHalfOunce()));
		rowMenu.getCustomElements().setValueLocal("ouncedonation", String.valueOf(item.getPriceOunce()));
		
		rowMenu.getCustomElements().setValueLocal("dispensaryid", String.valueOf(item.getDispensaryId()));
		rowMenu.getCustomElements().setValueLocal("menuitemid", String.valueOf(item.getId()));
		rowMenu.getCustomElements().setValueLocal("menuitemcategoryid", String.valueOf(item.getMenuItemCategoryId()));
	}
	public static void writeSpreadSheet(String filePath, Collection<Dispensary> dispensaries) throws Exception{
		int sheetCounter = 1;		
		File xlFile = new File(filePath);
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet storeSheet = workBook.createSheet("Store - " + (sheetCounter++));
		HSSFSheet menuSheet = workBook.createSheet("MenuItems - " + (sheetCounter++));
		int storeSheetCellNum = 0, menuSheetCellNum=0;
		int storeSheetRowNum = 0, menuSheetRowNum=0;
		HSSFFont font = workBook.createFont();
		font.setBoldweight(Short.valueOf("2"));
		Row storeSheetRow = storeSheet.createRow(storeSheetRowNum++); 
		Row menuSheetRow = menuSheet.createRow(menuSheetRowNum++);
		
		Cell menusheetDispensaryCell = menuSheetRow.createCell(menuSheetCellNum++); menusheetDispensaryCell.setCellValue("Dispensary");
		Cell menuSheetNameCell = menuSheetRow.createCell(menuSheetCellNum++); menuSheetNameCell.setCellValue("Name");
		Cell inStockCell = menuSheetRow.createCell(menuSheetCellNum++); inStockCell.setCellValue("In Stock");
		Cell soldOutCell = menuSheetRow.createCell(menuSheetCellNum++); soldOutCell.setCellValue("Sold Out");
		Cell imageCell = menuSheetRow.createCell(menuSheetCellNum++); imageCell.setCellValue("Image");
		Cell flowersCell = menuSheetRow.createCell(menuSheetCellNum++); flowersCell.setCellValue("Flowers");
		
		Cell indicaCell = menuSheetRow.createCell(menuSheetCellNum++); indicaCell.setCellValue("Indica");
		Cell sativaCell = menuSheetRow.createCell(menuSheetCellNum++); sativaCell.setCellValue("Sativa");
		Cell hybridCell = menuSheetRow.createCell(menuSheetCellNum++); hybridCell.setCellValue("Hybrid");
		Cell ediblesCell = menuSheetRow.createCell(menuSheetCellNum++); ediblesCell.setCellValue("Edibles");
		Cell concentratesCell = menuSheetRow.createCell(menuSheetCellNum++); concentratesCell.setCellValue("Concentrates");
		Cell drinkCell = menuSheetRow.createCell(menuSheetCellNum++); drinkCell.setCellValue("Drink");
		Cell clonesCell = menuSheetRow.createCell(menuSheetCellNum++); clonesCell.setCellValue("Clones");
		Cell seedsCell = menuSheetRow.createCell(menuSheetCellNum++); seedsCell.setCellValue("Seeds");
		Cell tinctureCell = menuSheetRow.createCell(menuSheetCellNum++); tinctureCell.setCellValue("Tincture");
		Cell gearCell = menuSheetRow.createCell(menuSheetCellNum++); gearCell.setCellValue("Gear");
		Cell accessoriesCell = menuSheetRow.createCell(menuSheetCellNum++); accessoriesCell.setCellValue("Accessories");
		Cell topicalsCell = menuSheetRow.createCell(menuSheetCellNum++); topicalsCell.setCellValue("Topicals");
		Cell prerollCell = menuSheetRow.createCell(menuSheetCellNum++); prerollCell.setCellValue("Preroll");
		Cell waxCell = menuSheetRow.createCell(menuSheetCellNum++); waxCell.setCellValue("Wax");
		
		Cell descriptionCell = menuSheetRow.createCell(menuSheetCellNum++); descriptionCell.setCellValue("Description");
		Cell gramDonationCell = menuSheetRow.createCell(menuSheetCellNum++); gramDonationCell.setCellValue("Gram Donation");
		Cell eighthDonationCell = menuSheetRow.createCell(menuSheetCellNum++); eighthDonationCell.setCellValue("Eighth Donation");
		Cell quarterDonationCell = menuSheetRow.createCell(menuSheetCellNum++); quarterDonationCell.setCellValue("Quarter Donation");
		Cell halfOunceDonationCell = menuSheetRow.createCell(menuSheetCellNum++); halfOunceDonationCell.setCellValue("Half Ounce Donation");
		Cell ounceDonationCell = menuSheetRow.createCell(menuSheetCellNum++); ounceDonationCell.setCellValue("Ounce Donation");
		
		
		Cell nameCell = storeSheetRow.createCell(storeSheetCellNum++); nameCell.setCellValue("Name");
		Cell phoneCell = storeSheetRow.createCell(storeSheetCellNum++); phoneCell.setCellValue("Phone");
		Cell websiteCell = storeSheetRow.createCell(storeSheetCellNum++); websiteCell.setCellValue("Website");
		Cell emailCell = storeSheetRow.createCell(storeSheetCellNum++); emailCell.setCellValue("Email");
		Cell streetCell = storeSheetRow.createCell(storeSheetCellNum++); streetCell.setCellValue("Street");
		Cell street2Cell = storeSheetRow.createCell(storeSheetCellNum++); street2Cell.setCellValue("Street2");
		Cell cityCell = storeSheetRow.createCell(storeSheetCellNum++); cityCell.setCellValue("City");
		Cell stateCell = storeSheetRow.createCell(storeSheetCellNum++); stateCell.setCellValue("State");
		Cell zipCell = storeSheetRow.createCell(storeSheetCellNum++); zipCell.setCellValue("Zip");
		Cell facebookCell = storeSheetRow.createCell(storeSheetCellNum++); facebookCell.setCellValue("Facebook");
		Cell twitterCell = storeSheetRow.createCell(storeSheetCellNum++); twitterCell.setCellValue("Twitter");
		Cell instagramCell = storeSheetRow.createCell(storeSheetCellNum++); instagramCell.setCellValue("Instagram");
		Cell creditCardCell = storeSheetRow.createCell(storeSheetCellNum++); creditCardCell.setCellValue("Credit Card");
		Cell handicapCell = storeSheetRow.createCell(storeSheetCellNum++); handicapCell.setCellValue("Handicap Accessible");
		Cell securityCell = storeSheetRow.createCell(storeSheetCellNum++); securityCell.setCellValue("Security Guard");
		Cell photoCell = storeSheetRow.createCell(storeSheetCellNum++); photoCell.setCellValue("Photos");
		Cell labTestedCell = storeSheetRow.createCell(storeSheetCellNum++); labTestedCell.setCellValue("Lab Tested");
		Cell adultCell = storeSheetRow.createCell(storeSheetCellNum++); adultCell.setCellValue("18+");
		Cell deliveryCell = storeSheetRow.createCell(storeSheetCellNum++); deliveryCell.setCellValue("Delivery");
		Cell monOpenCell = storeSheetRow.createCell(storeSheetCellNum++); monOpenCell.setCellValue("Open Mon");
		Cell monCloseCell = storeSheetRow.createCell(storeSheetCellNum++); monCloseCell.setCellValue("Close Mon");
		Cell tueOpenCell = storeSheetRow.createCell(storeSheetCellNum++); tueOpenCell.setCellValue("Open Tues");
		Cell tueCloseCell = storeSheetRow.createCell(storeSheetCellNum++); tueCloseCell.setCellValue("Close Tues");
		Cell wedOpenCell = storeSheetRow.createCell(storeSheetCellNum++); wedOpenCell.setCellValue("Open Wed");
		Cell wedCloseCell = storeSheetRow.createCell(storeSheetCellNum++); wedCloseCell.setCellValue("Close Wed");
		Cell thuOpenCell = storeSheetRow.createCell(storeSheetCellNum++); thuOpenCell.setCellValue("Open Thu");
		Cell thuCloseCell = storeSheetRow.createCell(storeSheetCellNum++); thuCloseCell.setCellValue("Close Thu");
		Cell friOpenCell = storeSheetRow.createCell(storeSheetCellNum++); friOpenCell.setCellValue("Open Fri");
		Cell friCloseCell = storeSheetRow.createCell(storeSheetCellNum++); friCloseCell.setCellValue("Close Fri");
		Cell satOpenCell = storeSheetRow.createCell(storeSheetCellNum++); satOpenCell.setCellValue("Open Sat");
		Cell satCloseCell = storeSheetRow.createCell(storeSheetCellNum++); satCloseCell.setCellValue("Close Sat");
		Cell sunOpenCell = storeSheetRow.createCell(storeSheetCellNum++); sunOpenCell.setCellValue("Open Sun");
		Cell sunCloseCell = storeSheetRow.createCell(storeSheetCellNum++); sunCloseCell.setCellValue("Close Sun");
		//((HSSFCell)nameCell).setCellStyle(HSSFCellStyle.)
		for(Dispensary obj : dispensaries){
			storeSheetRow = storeSheet.createRow(storeSheetRowNum++); 
			storeSheetCellNum = 0;
			nameCell = storeSheetRow.createCell(storeSheetCellNum++); nameCell.setCellValue(obj.getName());
			phoneCell = storeSheetRow.createCell(storeSheetCellNum++); phoneCell.setCellValue(obj.getPhone());
			websiteCell = storeSheetRow.createCell(storeSheetCellNum++); websiteCell.setCellValue(obj.getWebsite());
			emailCell = storeSheetRow.createCell(storeSheetCellNum++); emailCell.setCellValue(obj.getEmail());
			streetCell = storeSheetRow.createCell(storeSheetCellNum++); streetCell.setCellValue(((DispensaryObject)obj).getAddress().getStreetAddress());
			street2Cell = storeSheetRow.createCell(storeSheetCellNum++); street2Cell.setCellValue("");
			cityCell = storeSheetRow.createCell(storeSheetCellNum++); cityCell.setCellValue(((DispensaryObject)obj).getAddress().getCity());
			stateCell = storeSheetRow.createCell(storeSheetCellNum++); stateCell.setCellValue(((DispensaryObject)obj).getAddress().getState());
			zipCell = storeSheetRow.createCell(storeSheetCellNum++); zipCell.setCellValue(((DispensaryObject)obj).getAddress().getZip());
			facebookCell = storeSheetRow.createCell(storeSheetCellNum++); facebookCell.setCellValue(obj.getFacebookURL());
			twitterCell = storeSheetRow.createCell(storeSheetCellNum++); twitterCell.setCellValue(obj.getTwitterURL());
			instagramCell = storeSheetRow.createCell(storeSheetCellNum++); instagramCell.setCellValue(obj.getInstagramURL());
			creditCardCell = storeSheetRow.createCell(storeSheetCellNum++); creditCardCell.setCellValue(obj.isCreditCardSupport()?"":"X");
			handicapCell = storeSheetRow.createCell(storeSheetCellNum++); handicapCell.setCellValue(obj.isHandicapSupport()?"":"X");
			securityCell = storeSheetRow.createCell(storeSheetCellNum++); securityCell.setCellValue(obj.isSecurityGuardSupport()?"":"X");
			photoCell = storeSheetRow.createCell(storeSheetCellNum++); photoCell.setCellValue(obj.isPhotoAvailable()?"":"X");
			labTestedCell = storeSheetRow.createCell(storeSheetCellNum++); labTestedCell.setCellValue(obj.isLabTested()?"":"X");
			adultCell = storeSheetRow.createCell(storeSheetCellNum++); adultCell.setCellValue(obj.isForAdult()?"":"X");
			deliveryCell = storeSheetRow.createCell(storeSheetCellNum++); deliveryCell.setCellValue(obj.isDeliverySupport()?"":"X");
			monOpenCell = storeSheetRow.createCell(storeSheetCellNum++); monOpenCell.setCellValue(obj.getMondayOpen());
			monCloseCell = storeSheetRow.createCell(storeSheetCellNum++); monCloseCell.setCellValue(obj.getMondayClose());
			tueOpenCell = storeSheetRow.createCell(storeSheetCellNum++); tueOpenCell.setCellValue(obj.getTuesdayOpen());
			tueCloseCell = storeSheetRow.createCell(storeSheetCellNum++); tueCloseCell.setCellValue(obj.getTuesdayClose());
			wedOpenCell = storeSheetRow.createCell(storeSheetCellNum++); wedOpenCell.setCellValue(obj.getWednesdayOpen());
			wedCloseCell = storeSheetRow.createCell(storeSheetCellNum++); wedCloseCell.setCellValue(obj.getWednesdayOpen());
			thuOpenCell = storeSheetRow.createCell(storeSheetCellNum++); thuOpenCell.setCellValue(obj.getThursdayOpen());
			thuCloseCell = storeSheetRow.createCell(storeSheetCellNum++); thuCloseCell.setCellValue(obj.getThursdayClose());
			friOpenCell = storeSheetRow.createCell(storeSheetCellNum++); friOpenCell.setCellValue(obj.getFridayOpen());
			friCloseCell = storeSheetRow.createCell(storeSheetCellNum++); friCloseCell.setCellValue(obj.getFridayClose());
			satOpenCell = storeSheetRow.createCell(storeSheetCellNum++); satOpenCell.setCellValue(obj.getSaturdayOpen());
			satCloseCell = storeSheetRow.createCell(storeSheetCellNum++); satCloseCell.setCellValue(obj.getSaturdayClose());
			sunOpenCell = storeSheetRow.createCell(storeSheetCellNum++); sunOpenCell.setCellValue(obj.getSundayOpen());
			sunCloseCell = storeSheetRow.createCell(storeSheetCellNum++); sunCloseCell.setCellValue(obj.getSundayClose());
			
//			int numberOfRows = findMaximumCollectionSize(obj.getMenuItems());
//			int rowCounter=1;
			
			for(Menu menuItem : ((DispensaryObject)obj).getMenuItems()){
				if(menuItem.getName().equals("Vape Pen WHITE DIESEL"))
					System.out.println("Gotcha");
				menuSheetCellNum = 0;
				menuSheetRow = menuSheet.createRow(menuSheetRowNum++);
				
				menusheetDispensaryCell = menuSheetRow.createCell(menuSheetCellNum++); menusheetDispensaryCell.setCellValue(obj.getName());
				menuSheetNameCell = menuSheetRow.createCell(menuSheetCellNum++); menuSheetNameCell.setCellValue(menuItem.getName());
				inStockCell = menuSheetRow.createCell(menuSheetCellNum++); inStockCell.setCellValue("X");
				soldOutCell = menuSheetRow.createCell(menuSheetCellNum++); soldOutCell.setCellValue("");
				imageCell = menuSheetRow.createCell(menuSheetCellNum++); imageCell.setCellValue(((MenuItem)menuItem).getPictures()!=null && !((MenuItem)menuItem).getPictures().isEmpty() ? ((MenuItem)menuItem).getPictures().iterator().next().getImage().getUrl():"");
				flowersCell = menuSheetRow.createCell(menuSheetCellNum++); flowersCell.setCellValue(menuItem.getMenuItemCategoryId()==1 || menuItem.getMenuItemCategoryId()==2 ? "X":"");
				
				indicaCell = menuSheetRow.createCell(menuSheetCellNum++); indicaCell.setCellValue(menuItem.getMenuItemCategoryId()==1?"X":"");
				sativaCell = menuSheetRow.createCell(menuSheetCellNum++); sativaCell.setCellValue(menuItem.getMenuItemCategoryId()==2?"X":"");
				hybridCell = menuSheetRow.createCell(menuSheetCellNum++); hybridCell.setCellValue(menuItem.getMenuItemCategoryId()==3?"X":"");
				ediblesCell = menuSheetRow.createCell(menuSheetCellNum++); ediblesCell.setCellValue(menuItem.getMenuItemCategoryId()==4?"X":"");
				concentratesCell = menuSheetRow.createCell(menuSheetCellNum++); concentratesCell.setCellValue(menuItem.getMenuItemCategoryId()==5?"X":"");
				drinkCell = menuSheetRow.createCell(menuSheetCellNum++); drinkCell.setCellValue(menuItem.getMenuItemCategoryId()==6?"X":"");
				clonesCell = menuSheetRow.createCell(menuSheetCellNum++); clonesCell.setCellValue(menuItem.getMenuItemCategoryId()==7?"X":"");
				seedsCell = menuSheetRow.createCell(menuSheetCellNum++); seedsCell.setCellValue(menuItem.getMenuItemCategoryId()==8?"X":"");
				tinctureCell = menuSheetRow.createCell(menuSheetCellNum++); tinctureCell.setCellValue(menuItem.getMenuItemCategoryId()==9?"X":"");
				gearCell = menuSheetRow.createCell(menuSheetCellNum++); gearCell.setCellValue(menuItem.getMenuItemCategoryId()==10?"X":"");
				accessoriesCell = menuSheetRow.createCell(menuSheetCellNum++); accessoriesCell.setCellValue(menuItem.getMenuItemCategoryId()==10?"X":"");
				topicalsCell = menuSheetRow.createCell(menuSheetCellNum++); topicalsCell.setCellValue(menuItem.getMenuItemCategoryId()==11?"X":"");
				prerollCell = menuSheetRow.createCell(menuSheetCellNum++); prerollCell.setCellValue(menuItem.getMenuItemCategoryId()==12?"X":"");
				waxCell = menuSheetRow.createCell(menuSheetCellNum++); waxCell.setCellValue(menuItem.getMenuItemCategoryId()==13?"X":"");
				
				descriptionCell = menuSheetRow.createCell(menuSheetCellNum++);descriptionCell.setCellValue(menuItem.getDescription());
				gramDonationCell = menuSheetRow.createCell(menuSheetCellNum++);gramDonationCell.setCellValue(menuItem.getPriceGram());
				eighthDonationCell = menuSheetRow.createCell(menuSheetCellNum++);eighthDonationCell.setCellValue(menuItem.getPriceEighth());
				quarterDonationCell = menuSheetRow.createCell(menuSheetCellNum++);quarterDonationCell.setCellValue(menuItem.getPriceQuarter());
				halfOunceDonationCell = menuSheetRow.createCell(menuSheetCellNum++);halfOunceDonationCell.setCellValue(menuItem.getPriceHalfOunce());
				ounceDonationCell = menuSheetRow.createCell(menuSheetCellNum++);ounceDonationCell.setCellValue(menuItem.getPriceOunce());
			}
		}
		FileOutputStream outs = new FileOutputStream(xlFile);
		workBook.write(outs);
		outs.close();
	}
	private static DispensaryObject createDispensaryFromSpreadsheetRow(ListEntry row){
		String name = row.getCustomElements().getValue("name");
    	String phone = row.getCustomElements().getValue("phone");
    	String website = row.getCustomElements().getValue("website");
    	String email = row.getCustomElements().getValue("email");
    	String street = row.getCustomElements().getValue("street");
    	String city = row.getCustomElements().getValue("city");
    	String state = row.getCustomElements().getValue("state");
    	String zip = row.getCustomElements().getValue("zipcode");
    	String facebook = row.getCustomElements().getValue("facebook");
    	String twitter = row.getCustomElements().getValue("twitter");
    	String instagram = row.getCustomElements().getValue("instagram");
    	boolean creditCard = row.getCustomElements().getValue("creditcard")!=null && row.getCustomElements().getValue("creditcard").isEmpty()?false:true;
    	boolean handicap = row.getCustomElements().getValue("handicapaccessible")!=null && row.getCustomElements().getValue("handicapaccessible").isEmpty()?false:true;
    	boolean security = row.getCustomElements().getValue("securityguard")!=null && row.getCustomElements().getValue("securityguard").isEmpty()?false:true;
    	boolean photos = row.getCustomElements().getValue("photos")!=null && row.getCustomElements().getValue("photos").isEmpty()?false:true;
    	boolean delivery = row.getCustomElements().getValue("delivery")!=null && row.getCustomElements().getValue("delivery").isEmpty()?false:true;
    	boolean labTested = row.getCustomElements().getValue("labtested")!=null && row.getCustomElements().getValue("labtested").isEmpty()?false:true;
    	boolean adult = row.getCustomElements().getValue("foradult")!=null && row.getCustomElements().getValue("foradult").isEmpty()?false:true;
    	String sunOpen = row.getCustomElements().getValue("opensun");
    	String sunClose = row.getCustomElements().getValue("closesun");
    	String monOpen = row.getCustomElements().getValue("openmon");
    	String monClose = row.getCustomElements().getValue("closemon");
    	String tueOpen = row.getCustomElements().getValue("opentues");
    	String tueClose = row.getCustomElements().getValue("closetues");
    	String wedOpen = row.getCustomElements().getValue("openwed");
    	String wedClose = row.getCustomElements().getValue("closewed");
    	String thuOpen = row.getCustomElements().getValue("openthu");
    	String thuClose = row.getCustomElements().getValue("closethu");
    	String friOpen = row.getCustomElements().getValue("openfri");
    	String friClose = row.getCustomElements().getValue("closefri");
    	String satOpen = row.getCustomElements().getValue("opensat");
    	String satClose = row.getCustomElements().getValue("closesat");
    	Long dispensaryId = Long.parseLong(row.getCustomElements().getValue("dispensaryid"));
    	String url = row.getCustomElements().getValue("weedmapsurl");
    	return new DispensaryObject(dispensaryId, name, phone, email, website, new Address(street, null, city, state, zip), 
				facebook, twitter, instagram, creditCard, handicap, security, photos, labTested, adult, delivery, sunOpen, sunClose, monOpen, monClose, tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen, friClose, satOpen, satClose, url);
	}
}
