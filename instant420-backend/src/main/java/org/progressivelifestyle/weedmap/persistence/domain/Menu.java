package org.progressivelifestyle.weedmap.persistence.domain;


public interface Menu {

	public abstract String getName();

	public abstract Long getDispensaryId();

	public abstract Long getMenuItemCategoryId();

	public abstract int getPriceEighth();

	public abstract int getPriceGram();

	public abstract int getPriceHalfGram();

	public abstract int getPriceHalfOunce();

	public abstract int getPriceOunce();

	public abstract int getPriceQuarter();

	public abstract int getPriceUnit();

	public abstract String getCategoryName();

	public abstract String getDescription();

	public abstract String getStrainId();

	public abstract Long getId();
	
	public abstract void setDispensary(BaseEntity dispensary);
	
	public boolean isLogicallyEquals(Menu menu);

}