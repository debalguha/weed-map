package org.instant420.web.domain;

public class MenuItemSearchObject extends SearchObject{
	private Long id;
	private String name;
	private int priceEighth;
	private int priceGram;
	private int priceHalfGram;
	private int priceHalfOunce;
	private int priceOunce;
	private int priceQuarter;
	private int priceUnit;
	private String pictureURL;
	private String category;
	private String subCategoryName;
	private int numberOfDispensary;
	private Long strainId;
	private String description;
	public MenuItemSearchObject(){
	}
	public MenuItemSearchObject(Long id, String name, int priceEighth, int priceGram, int priceHalfGram, int priceHalfOunce, int priceOunce, int priceQuarter, int priceUnit, String pictureURL, String category, String subCategoryName, int numberOfDispensary, Long strainId, String description) {
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
		this.pictureURL = pictureURL;
		this.category = category;
		this.subCategoryName = subCategoryName;
		this.numberOfDispensary = numberOfDispensary;
		this.strainId = strainId;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPriceEighth() {
		return priceEighth;
	}
	public void setPriceEighth(int priceEighth) {
		this.priceEighth = priceEighth;
	}
	public int getPriceGram() {
		return priceGram;
	}
	public void setPriceGram(int priceGram) {
		this.priceGram = priceGram;
	}
	public int getPriceHalfGram() {
		return priceHalfGram;
	}
	public void setPriceHalfGram(int priceHalfGram) {
		this.priceHalfGram = priceHalfGram;
	}
	public int getPriceHalfOunce() {
		return priceHalfOunce;
	}
	public void setPriceHalfOunce(int priceHalfOunce) {
		this.priceHalfOunce = priceHalfOunce;
	}
	public int getPriceOunce() {
		return priceOunce;
	}
	public void setPriceOunce(int priceOunce) {
		this.priceOunce = priceOunce;
	}
	public int getPriceQuarter() {
		return priceQuarter;
	}
	public void setPriceQuarter(int priceQuarter) {
		this.priceQuarter = priceQuarter;
	}
	public int getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(int priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getNumberOfDispensary() {
		return numberOfDispensary;
	}
	public void setNumberOfDispensary(int numberOfDispensary) {
		this.numberOfDispensary = numberOfDispensary;
	}
	public Long getStrainId() {
		return strainId;
	}
	public void setStrainId(Long strainId) {
		this.strainId = strainId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
}
