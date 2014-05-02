package org.instant420.web.domain;

public class MenuItemSearchObject extends SearchObject{
	private final String id;
	private final String name;
	private final String priceEighth;
	private final String priceGram;
	private final String priceHalfGram;
	private final String priceHalfOunce;
	private final String priceOunce;
	private final String priceQuarter;
	private final String priceUnit;
	private final String pictureUrl;
	private final String category;
	private final String numberOfDispensary;
	public MenuItemSearchObject(String id, String name, String priceEighth, String priceGram, String priceHalfGram, String priceHalfOunce, String priceOunce, String priceQuarter, String priceUnit, String pictureUrl, String category, String numberOfDispensary) {
		super();
		this.id = id;
		this.name = name;
		this.priceEighth = priceEighth;
		this.priceGram = priceGram;
		this.priceHalfGram = priceHalfGram;
		this.priceHalfOunce = priceHalfOunce;
		this.priceOunce = priceOunce;
		this.priceQuarter = priceQuarter;
		this.priceUnit = priceUnit;
		this.pictureUrl = pictureUrl;
		this.category = category;
		this.numberOfDispensary = numberOfDispensary;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPriceEighth() {
		return priceEighth;
	}
	public String getPriceGram() {
		return priceGram;
	}
	public String getPriceHalfGram() {
		return priceHalfGram;
	}
	public String getPriceHalfOunce() {
		return priceHalfOunce;
	}
	public String getPriceOunce() {
		return priceOunce;
	}
	public String getPriceQuarter() {
		return priceQuarter;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public String getCategory() {
		return category;
	}
	public String getNumberOfDispensary() {
		return numberOfDispensary;
	}
	
}
