package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="menuitemcategoryentity")
//@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="dispensary")
public class MenuItemCategoryEntity extends BaseEntity{
	@Id
	private Long id;
	@Column(nullable=false, unique = true)
	private String categoryName;
	
	private Date creationDate;
	private Date lastUpdateDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		MenuItemCategoryEntity other = (MenuItemCategoryEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	@Override
	public String toString() {
		return "MenuItemCategoryEntity [id=" + id + ", categoryName=" + categoryName + "]";
	}
	@Override
	public void setHitCount(Integer hitCount) {
	}
	@Override
	public Integer getHitCount() {
		return 0;
	}
	
	
}
