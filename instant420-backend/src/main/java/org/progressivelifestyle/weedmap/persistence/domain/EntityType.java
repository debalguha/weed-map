package org.progressivelifestyle.weedmap.persistence.domain;

public enum EntityType {
	DISPENSARY, MEDICINE, ENTERTAINMENT, ACCESORIES, FLOWER, CONCENTRATE, EDIBLE;
	public static EntityType fromName(String name){
		if(name.toUpperCase().equals(DISPENSARY.name()))
			return DISPENSARY;
		else if(name.toUpperCase().equals(MEDICINE.name()))
			return MEDICINE;
		else if(name.toUpperCase().equals(ENTERTAINMENT.name()))
			return ENTERTAINMENT;
		else if(name.toUpperCase().equals(ACCESORIES.name()))
			return ACCESORIES;
		else if(name.toUpperCase().equals(FLOWER.name()))
			return FLOWER;
		else if(name.toUpperCase().equals(CONCENTRATE.name()))
			return CONCENTRATE;
		else if(name.toUpperCase().equals(EDIBLE.name()))
			return EDIBLE;
		else
			return null;
	}
	
}
