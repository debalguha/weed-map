package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="menuitementity")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="dispensary")
public class MenuItemEntity implements BaseEntity, Menu{
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
	@Lob
	@Column(columnDefinition="TEXT")
	private String description;
	private String strainId;
	private String pictureURL;
	
	private Date creationDate;
	private Date lastUpdateDate;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="menuItemCategoryId")
	private MenuItemCategoryEntity menuItemCategory;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private DispensaryEntity dispensary;
	public Long getId() {
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
	/*@Override
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
	}*/
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
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public Long getDispensaryId() {
		return null;
	}
	public Long getMenuItemCategoryId() {
		return menuItemCategory.getId();
	}
	public String getCategoryName() {
		return menuItemCategory.getCategoryName();
	}
	public boolean isLogicallyEquals(Menu obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItemEntity other = (MenuItemEntity) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (menuItemCategory == null) {
			if (other.menuItemCategory != null)
				return false;
		} else if (!menuItemCategory.equals(other.menuItemCategory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pictureURL == null) {
			if (other.pictureURL != null)
				return false;
		} else if (!pictureURL.equals(other.pictureURL))
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
	public DispensaryEntity getDispensary() {
		return dispensary;
	}
	public void setDispensary(BaseEntity dispensary) {
		this.dispensary = (DispensaryEntity)dispensary;
	}
	@Override
	public String toString() {
		return "MenuItemEntity [id=" + id + ", name=" + name + ", priceEighth=" + priceEighth + ", priceGram=" + priceGram + ", priceHalfGram=" + priceHalfGram + ", priceHalfOunce=" + priceHalfOunce + ", priceOunce=" + priceOunce + ", priceQuarter=" + priceQuarter
				+ ", priceUnit=" + priceUnit + ", description=" + description + ", strainId=" + strainId + ", pictureURL=" + pictureURL + ", menuItemCategory=" + menuItemCategory + ", dispensary=" + dispensary + "]";
	}
	
}
