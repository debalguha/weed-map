package org.progressivelifestyle.weedmap.persistence.domain;

import java.util.Date;

public interface BaseEntity {
	public Date getCreationDate();
	public Date getLastUpdateDate();
	public void setCreationDate(Date creationDate);
	public void setLastUpdateDate(Date lastUpdateDate);
	public Long getId();
}
