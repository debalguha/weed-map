package org.progressivelifestyle.weedmap.persistence.domain;

public enum EntityType {
	DISPENSARY, MEDICINE, ENTERTAINMENT, ACCESORIES, FLOWERS, CONCENTRATES, EDIBLES, UNKNOWN;
	public static EntityType fromName(String name){
		if(name.toUpperCase().equals(DISPENSARY.name()))
			return DISPENSARY;
		else if(name.toUpperCase().equals(MEDICINE.name()))
			return MEDICINE;
		else if(name.toUpperCase().equals(ENTERTAINMENT.name()))
			return ENTERTAINMENT;
		else if(name.toUpperCase().equals(ACCESORIES.name()))
			return ACCESORIES;
		else if(name.toUpperCase().equals(FLOWERS.name()))
			return FLOWERS;
		else if(name.toUpperCase().equals(CONCENTRATES.name()))
			return CONCENTRATES;
		else if(name.toUpperCase().equals(EDIBLES.name()))
			return EDIBLES;
		else
			return UNKNOWN;
	}
	
}
