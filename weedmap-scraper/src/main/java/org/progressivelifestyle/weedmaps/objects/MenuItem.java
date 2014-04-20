package org.progressivelifestyle.weedmaps.objects;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.progressivelifestyle.weedmap.persistence.domain.BaseEntity;
import org.progressivelifestyle.weedmap.persistence.domain.Menu;

public class MenuItem implements Comparable<MenuItem>, Menu{
	private String name;
	private long id;
	@JsonProperty("dispensary_id")
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
	@JsonProperty("strain_id")
	private String strainId;
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
	public String getStrainId() {
		return strainId;
	}
	public void setStrainId(String strainId) {
		this.strainId = strainId;
	}
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int compareTo(MenuItem o) {
		return this.name.compareTo(o.name);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((menuItemCategoryId == null) ? 0 : menuItemCategoryId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pictures == null) ? 0 : pictures.hashCode());
		result = prime * result + priceEighth;
		result = prime * result + priceGram;
		result = prime * result + priceHalfGram;
		result = prime * result + priceHalfOunce;
		result = prime * result + priceOunce;
		result = prime * result + priceQuarter;
		result = prime * result + priceUnit;
		result = prime * result + ((strainId == null) ? 0 : strainId.hashCode());
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
		MenuItem other = (MenuItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (menuItemCategoryId == null) {
			if (other.menuItemCategoryId != null)
				return false;
		} else if (!menuItemCategoryId.equals(other.menuItemCategoryId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (priceEighth != other.priceEighth)
			return false;
		if (priceGram != other.priceGram)
			return false;
		if (priceHalfGram != other.priceHalfGram)
			return false;
		if (priceHalfOunce != other.priceHalfOunce)
			return false;
		if (priceOunce != other.priceOunce)
			return false;
		if (priceQuarter != other.priceQuarter)
			return false;
		if (priceUnit != other.priceUnit)
			return false;
		if (strainId == null) {
			if (other.strainId != null)
				return false;
		} else if (!strainId.equals(other.strainId))
			return false;
		return true;
	}
	public void setDispensary(BaseEntity dispensary) {
		throw new UnsupportedOperationException("This facility makes sense with persistence model.");
	}
	public boolean isLogicallyEquals(Menu menu) {
		throw new UnsupportedOperationException("This operation makes sense for persistence entities.");
	}
	
	
}
