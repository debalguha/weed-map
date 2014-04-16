package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class MenuItemEntity implements BaseEntity{
	@Id
	private long id;
	private String name;
	private int priceEighth;
	private int priceGram;
	private int priceHalfGram;
	private int priceHalfOunce;
	private int priceOunce;
	private int priceQuarter;
	private int priceUnit;
	private String description;
	private String strainId;
	
	private Date creationDate;
	private Date lastUpdateDate;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="menuItemCategoryId", insertable=false, updatable=false,nullable=false,unique=false)
	private MenuItemCategoryEntity menuItemCategory;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		MenuItemEntity other = (MenuItemEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public MenuItemCategoryEntity getMenuItemCategory() {
		return menuItemCategory;
	}
	public void setMenuItemCategory(MenuItemCategoryEntity menuItemCategory) {
		this.menuItemCategory = menuItemCategory;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
}
