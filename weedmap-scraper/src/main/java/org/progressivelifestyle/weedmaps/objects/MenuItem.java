package org.progressivelifestyle.weedmaps.objects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;

public class MenuItem {
	private String name;
	@JsonProperty("id")
	private Long dispensaryId;
	@JsonProperty("menu_item_category_id")
	private Long menuItemCategoryId;
	@JsonProperty("price_eighth")
	private int priceEighth;
	@JsonProperty("price_gram")
	private int priceGram;
	@JsonProperty("price_half_gram")
	private int priceHalfGram;
	@JsonProperty("price_half_ounce")
	private int priceHalfOunce;
	@JsonProperty("price_ounce")
	private int priceOunce;
	@JsonProperty("price_quarter")
	private int priceQuarter;
	@JsonProperty("price_unit")
	private int priceUnit;
	@JsonProperty("published")
	private boolean inStock;
	@JsonProperty("approved_by_admin")
	private boolean approvedByAdmin; 
	@JsonProperty("body")
	private String description;
	private String categoryName;
	
	private Collection<Picture> pictures;
	private Map<String , Object> otherProperties = new HashMap<String , Object>();

	public Collection<Picture> getPictures() {
		return pictures;
	}
	public void setPictures(Collection<Picture> pictures) {
		this.pictures = pictures;
	}
    @JsonAnyGetter
    public Map<String , Object> any() {
        return otherProperties;
    }
 
    @JsonAnySetter
    public void set(String name, Object value) {
        otherProperties.put(name, value);
    }
	public String getName() {
		return name;
	}
	public Long getDispensaryId() {
		return dispensaryId;
	}
	public Long getMenuItemCategoryId() {
		return menuItemCategoryId;
	}
	public int getPriceEighth() {
		return priceEighth;
	}
	public int getPriceGram() {
		return priceGram;
	}
	public int getPriceHalfGram() {
		return priceHalfGram;
	}
	public int getPriceHalfOunce() {
		return priceHalfOunce;
	}
	public int getPriceOunce() {
		return priceOunce;
	}
	public int getPriceQuarter() {
		return priceQuarter;
	}
	public int getPriceUnit() {
		return priceUnit;
	}
	public Map<String, Object> getOtherProperties() {
		return otherProperties;
	}
	public void setOtherProperties(Map<String, Object> otherProperties) {
		this.otherProperties = otherProperties;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDispensaryId(Long dispensaryId) {
		this.dispensaryId = dispensaryId;
	}
	public void setMenuItemCategoryId(Long menuItemCategoryId) {
		this.menuItemCategoryId = menuItemCategoryId;
	}
	public void setPriceEighth(int priceEighth) {
		this.priceEighth = priceEighth;
	}
	public void setPriceGram(int priceGram) {
		this.priceGram = priceGram;
	}
	public void setPriceHalfGram(int priceHalfGram) {
		this.priceHalfGram = priceHalfGram;
	}
	public void setPriceHalfOunce(int priceHalfOunce) {
		this.priceHalfOunce = priceHalfOunce;
	}
	public void setPriceOunce(int priceOunce) {
		this.priceOunce = priceOunce;
	}
	public void setPriceQuarter(int priceQuarter) {
		this.priceQuarter = priceQuarter;
	}
	public void setPriceUnit(int priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public boolean isInStock() {
		return inStock;
	}
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
	public boolean isApprovedByAdmin() {
		return approvedByAdmin;
	}
	public void setApprovedByAdmin(boolean approvedByAdmin) {
		this.approvedByAdmin = approvedByAdmin;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
