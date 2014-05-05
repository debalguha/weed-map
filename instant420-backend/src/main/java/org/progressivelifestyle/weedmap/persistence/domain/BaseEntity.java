package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;

public abstract class BaseEntity {
	public abstract Date getCreationDate();
	public abstract Date getLastUpdateDate();
	public abstract void setCreationDate(Date creationDate);
	public abstract void setLastUpdateDate(Date lastUpdateDate);
	public abstract Long getId();
	public abstract void setHitCount(Integer hitCount);
	public abstract Integer getHitCount();
	
}
