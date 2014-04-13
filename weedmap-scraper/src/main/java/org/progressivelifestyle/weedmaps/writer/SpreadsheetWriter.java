package org.progressivelifestyle.weedmaps.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.progressivelifestyle.weedmaps.objects.DispensaryObject;
import org.progressivelifestyle.weedmaps.objects.MenuItem;

public class SpreadsheetWriter {
	public static void writeSpreadSheet(String filePath, Collection<DispensaryObject> dispensaries) throws Exception{
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
		Cell concentratesCell = menuSheetRow.createCell(menuSheetCellNum++); concentratesCell.setCellValue("Concentrates");
		Cell ediblesCell = menuSheetRow.createCell(menuSheetCellNum++); ediblesCell.setCellValue("Edibles");
		Cell clonesCell = menuSheetRow.createCell(menuSheetCellNum++); clonesCell.setCellValue("Clones");
		Cell seedsCell = menuSheetRow.createCell(menuSheetCellNum++); seedsCell.setCellValue("Seeds");
		Cell accessoriesCell = menuSheetRow.createCell(menuSheetCellNum++); accessoriesCell.setCellValue("Accessories");
		Cell sativaCell = menuSheetRow.createCell(menuSheetCellNum++); sativaCell.setCellValue("Sativa");
		Cell indicaCell = menuSheetRow.createCell(menuSheetCellNum++); indicaCell.setCellValue("Indica");
		Cell hybridCell = menuSheetRow.createCell(menuSheetCellNum++); hybridCell.setCellValue("Hybrid");
		
		Cell drinkCell = menuSheetRow.createCell(menuSheetCellNum++); drinkCell.setCellValue("Drink");
		Cell tinctureCell = menuSheetRow.createCell(menuSheetCellNum++); tinctureCell.setCellValue("Tincture");
		Cell gearCell = menuSheetRow.createCell(menuSheetCellNum++); gearCell.setCellValue("Gear");
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
		for(DispensaryObject obj : dispensaries){
			storeSheetRow = storeSheet.createRow(storeSheetRowNum++); 
			storeSheetCellNum = 0;
			nameCell = storeSheetRow.createCell(storeSheetCellNum++); nameCell.setCellValue(obj.getName());
			phoneCell = storeSheetRow.createCell(storeSheetCellNum++); phoneCell.setCellValue(obj.getPhone());
			websiteCell = storeSheetRow.createCell(storeSheetCellNum++); websiteCell.setCellValue(obj.getWebsite());
			emailCell = storeSheetRow.createCell(storeSheetCellNum++); emailCell.setCellValue(obj.getEmail());
			streetCell = storeSheetRow.createCell(storeSheetCellNum++); streetCell.setCellValue(obj.getAddress().getStreetAddress());
			street2Cell = storeSheetRow.createCell(storeSheetCellNum++); street2Cell.setCellValue("");
			cityCell = storeSheetRow.createCell(storeSheetCellNum++); cityCell.setCellValue(obj.getAddress().getCity());
			stateCell = storeSheetRow.createCell(storeSheetCellNum++); stateCell.setCellValue(obj.getAddress().getState());
			zipCell = storeSheetRow.createCell(storeSheetCellNum++); zipCell.setCellValue(obj.getAddress().getZip());
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
			
			for(MenuItem menuItem : obj.getMenuItems()){
				menuSheetCellNum = 0;
				menuSheetRow = menuSheet.createRow(menuSheetRowNum++);
				
				menusheetDispensaryCell = menuSheetRow.createCell(menuSheetCellNum++); menusheetDispensaryCell.setCellValue(obj.getName());
				menuSheetNameCell = menuSheetRow.createCell(menuSheetCellNum++); menuSheetNameCell.setCellValue(menuItem.getName());
				inStockCell = menuSheetRow.createCell(menuSheetCellNum++); inStockCell.setCellValue(menuItem.isInStock()?"X":"");
				soldOutCell = menuSheetRow.createCell(menuSheetCellNum++); soldOutCell.setCellValue(menuItem.isApprovedByAdmin()?"":"X");
				imageCell = menuSheetRow.createCell(menuSheetCellNum++); imageCell.setCellValue("");
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

}
